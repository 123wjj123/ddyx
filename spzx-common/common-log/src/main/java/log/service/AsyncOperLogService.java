package log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

public interface AsyncOperLogService {

    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;
}
