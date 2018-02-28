package log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * log4j.rootLogger = [(日志下限)] (AppenderName1) (AppenderName2)...
 *
 * log4j.logger.(ChildLoggerName) = [(日志下限)] (AppenderName1) (AppenderName2)...
 * 子logger完全继承rootLogger的所有Appender，如不需要，子logger的appender中添加配置：logger.additivity.(ChildLoggerName) = false  【默认为true】
 *
 * appender:【输出日志目的地】
 *          ConsoleAppender 【输出到控制台】
 *          FileAppender 【输出到文件】
 * log4j.appender.AppenderName1.file = ""/文件路径
 * log4j.appender.AppenderName1.Encoding = 编码
 *          DailyRollingFileAppender 【输出到文件（按天rolling）】
 * log4j.appender.AppenderName1.DatePattern = 文件名
 *          RollingFileAppender 【输出到文件（按文件大小rolling）】
 *          WriteAppender 【以流形式输出】
 *
 * log4j.appender.AppenderName1.layout = PatternLayout
 * log4j.appender.AppenderName1.layout.ConversionPattern =      %p 日志优先级
 *                                                              %c appender名
 *                                                              %C 调用的类名
 *                                                              %l 线程名+类名+行数
 *                                                              %L 调用的行数
 *                                                              %x NDC
 *                                                              %X MDC
 *                                                              %d 日期
 *                                                              %m message
 *                                                              %M 调用方法名
 *                                                              %n 换行
 * layout:  PatternLayout
 *          SimpleLayout
 *          HTMLLayout
 *          TTCCLayout
 *
 *
 *
 *
 * Created by wyz on 2018/1/3.
 */
public class LogUtil {
    public static Logger infoLogger = Logger.getLogger("info");

    static  {
        PropertyConfigurator.configure("conf/log.properties");
    }
}
