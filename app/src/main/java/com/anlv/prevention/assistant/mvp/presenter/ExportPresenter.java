package com.anlv.prevention.assistant.mvp.presenter;

import android.app.Application;
import android.os.Environment;

import com.anlv.prevention.assistant.app.utils.ToolUtils;
import com.anlv.prevention.assistant.mvp.contract.ExportContract;
import com.anlv.prevention.assistant.mvp.model.api.entity.BaseResult;
import com.anlv.prevention.assistant.mvp.model.api.entity.Info;
import com.anlv.prevention.assistant.mvp.ui.adapter.InfoAdapter;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.sun.mail.util.MailSSLSocketFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.inject.Inject;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 02/07/2020 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ExportPresenter extends BasePresenter<ExportContract.Model, ExportContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private final static String SERVER = "smtp.exmail.qq.com";
    private final static String PORT = "465";
    private final static String FROM_USER = "fangyizhushou@anlv365.com";
    private final static String FROM_PASSWORD = "Qaz12345678";

    private InfoAdapter mAdapter;
    private String mailBody;

    @Inject
    ExportPresenter(ExportContract.Model model, ExportContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        this.mAdapter = null;
    }

    public void query(Date beginTime, Date endTime) {
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginTime);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endTime);
        endCalendar.set(Calendar.HOUR, 23);
        endCalendar.set(Calendar.MINUTE, 59);
        endCalendar.set(Calendar.SECOND, 59);
        mModel.queryData(TimeUtils.date2String(beginCalendar.getTime()), TimeUtils.date2String(endCalendar.getTime()))
                .compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResult<List<Info>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResult<List<Info>> result) {
                        if (result.isSucc()) {
                            mailBody = String.format("采集数据汇总：%s 至 %s",
                                    TimeUtils.date2String(beginCalendar.getTime(), "yyyy年M月d日HH时mm分ss秒"),
                                    TimeUtils.date2String(endCalendar.getTime(), "yyyy年M月d日HH时mm分ss秒"));
                            if (ObjectUtils.isEmpty(mAdapter)) {
                                mAdapter = new InfoAdapter(result.getResult());
                                mRootView.setAdapter(mAdapter);
                            } else {
                                mAdapter.updateDataList(result.getResult());
                            }
                        } else {
                            if (ObjectUtils.isEmpty(result.getMessage())) {
                                mRootView.showMessage("采集数据查询失败");
                            } else {
                                mRootView.showMessage(result.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * 获取导出数据数量
     */
    public int getExportCount() {
        if (ObjectUtils.isEmpty(mAdapter))
            return 0;
        return mAdapter.getItemCount();
    }

    /**
     * 导出数据
     */
    public void exportData(String mailAddress) {
        PermissionUtil.externalStorage(
                new PermissionUtil.RequestPermission() {
                    @Override
                    public void onRequestPermissionSuccess() {
                        File file = saveToCSV();
                        if (ObjectUtils.isEmpty(file)) {
                            mRootView.showMessage("导出数据失败");
                        } else {
                            sendEmail(mailAddress, "采集数据上报" + file.getName(), mailBody, file);
                        }
                    }

                    @Override
                    public void onRequestPermissionFailure(List<String> permissions) {
                        mRootView.showMessage("没有外部存取权限");
                    }

                    @Override
                    public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                        mRootView.showMessage("没有外部存取权限");
                    }
                }, mRootView.getRxPermissions(), mErrorHandler);
    }


    /**
     * 保存数据到CSV文件
     */
    private File saveToCSV() {
        if (!SDCardUtils.isSDCardEnableByEnvironment()) {
            Timber.i("SD卡不可用");
            return null;
        }
        File rootPath = new File(SDCardUtils.getSDCardPathByEnvironment(), "PreventionAssistant/data");
        if (!FileUtils.createOrExistsDir(rootPath)) {
            Timber.i("创建数据目录失败");
            return null;
        }
        File csvFile = new File(rootPath, String.format("%s.csv", TimeUtils.date2String(TimeUtils.getNowDate(), "yyyyMMddHHmmss")));
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile, false));
            // 添加头部名称
            bw.write(new String(new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF}));
            bw.write("管控区名称" + "," + "证件号码" + "," + "姓名" + "," + "电话号码"
                    + "," + "住址" + "," + "体温" + "," + "上报时间" + "," + "备注");
            bw.newLine();

            for (Info info : mAdapter.getDataList()) {
                bw.write(info.getAreaName() + "," + info.getCertificateNumber()
                        + "," + info.getName() + "," + info.getPhoneNumber()
                        + "," + info.getAddress() + "," + info.getTemperature()
                        + "," + TimeUtils.date2String(info.getCreateTime()) + "," + info.getRemark());
                bw.newLine();
            }
            bw.close();
            return csvFile;
        } catch (Exception e) {
            Timber.e(e, "CSV文件保存失败");
        }
        return null;
    }

    /**
     * 发送邮件
     *
     * @param mailAddress 接收方邮箱地址
     * @param subject     邮件标题
     * @param body        正文内容
     * @param file        发送的文件附件
     **/
    private void sendEmail(String mailAddress, String subject, String body, File file) {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            Properties props = new Properties();
            try {
                MailSSLSocketFactory sf = new MailSSLSocketFactory();
                sf.setTrustAllHosts(true);
                props.put("mail.smtp.ssl.enable", "true");
                props.put("mail.smtp.ssl.socketFactory", sf);
                props.put("mail.smtp.host", SERVER);
                props.put("mail.smtp.port", PORT);
                props.put("mail.smtp.auth", "true");
                Session session = Session.getDefaultInstance(props, null);
                MimeMessage msg = new MimeMessage(session);
                Transport transport = session.getTransport("smtp");
                transport.connect(SERVER, FROM_USER, FROM_PASSWORD);//建立与服务器连接
                msg.setSentDate(new Date());
                InternetAddress fromAddress = new InternetAddress(FROM_USER);
                msg.setFrom(fromAddress);
                InternetAddress[] toAddress = new InternetAddress[1];
                toAddress[0] = new InternetAddress(mailAddress);
                msg.setRecipients(Message.RecipientType.TO, toAddress);
                msg.setSubject(subject, "UTF-8");//设置邮件标题
                MimeMultipart multi = new MimeMultipart();//代表整个邮件
                BodyPart textBodyPart = new MimeBodyPart();//设置正文对象
                textBodyPart.setText(body);//设置正文
                multi.addBodyPart(textBodyPart);//添加正文到邮件
                if (FileUtils.isFileExists(file)) {
                    FileDataSource fds = new FileDataSource(file);//获取磁盘文件
                    BodyPart fileBodyPart = new MimeBodyPart();//创建BodyPart
                    fileBodyPart.setDataHandler(new DataHandler(fds));//将文件信息封装至BodyPart对象
                    String fileNameNew = MimeUtility.encodeText(fds.getName(), "utf-8", null);//设置文件名称显示编码，解决乱码问题
                    fileBodyPart.setFileName(fileNameNew);//设置邮件中显示的附件文件名
                    multi.addBodyPart(fileBodyPart);//将附件添加到邮件中
                }
                msg.setContent(multi);//将整个邮件添加到message中
                msg.saveChanges();
                transport.sendMessage(msg, msg.getAllRecipients());//发送邮件
                transport.close();
                emitter.onNext(true);
                emitter.onComplete();
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).compose(ToolUtils.applySchedulers(mRootView))
                .subscribe(result -> {
                    if (result) {
                        FileUtils.delete(file);
                        mRootView.showMessage("邮件发送成功");
                    } else {
                        mRootView.exportFail(file);
                        mRootView.showMessage("邮件发送失败");

                    }
                }, throwable -> {
                    Timber.e(throwable, "邮件发送失败");
                    mRootView.exportFail(file);
                });
    }
}
