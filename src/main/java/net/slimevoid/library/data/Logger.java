/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received a copy of the GNU
 * Lesser General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>
 */
package net.slimevoid.library.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.slimevoid.library.core.SlimevoidLib;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 * Slimevoid logging engine.<br>
 * Singleton pattern class.
 * 
 * @author ali4z
 */
public abstract class Logger {
    protected String       name;
    protected LoggerWriter writer;
    protected LogLevel     filterLevel;

    /**
     * Log level.<br>
     * All messages are DEBUG or higher.<br>
     * Stack trace errors are ERROR messages.
     * 
     * @author ali4z
     */
    public static enum LogLevel {
        DEBUG,
        WARNING,
        ERROR,
        INFO
    }

    protected Logger() {
        setName(this.getLoggerName());
    }

    /**
     * Gets the logger name
     * 
     * Implemented to return a mod specific name for logfile
     * 
     * @return Mod Name
     */
    protected abstract String getLoggerName();

    /**
     * Sets the filtering level based on a string.<br>
     * "DEBUG","INFO","WARNING","ERROR" are valid strings.
     * 
     * @param f
     *            log level string
     * @return true if string valid was valid. Defaults to INFO if not.
     */
    public boolean setFilterLevel(String f) {
        if (f.equals(LogLevel.DEBUG.name())) {
            filterLevel = LogLevel.DEBUG;
            return true;
        } else if (f.equals(LogLevel.INFO.name())) {
            filterLevel = LogLevel.INFO;
            return true;
        } else if (f.equals(LogLevel.WARNING.name())) {
            filterLevel = LogLevel.WARNING;
            return true;
        } else if (f.equals(LogLevel.ERROR.name())) {
            filterLevel = LogLevel.ERROR;
            return true;
        }

        filterLevel = LogLevel.INFO;
        return false;
    }

    private boolean filter(LogLevel lvl) {
        if (filterLevel == LogLevel.DEBUG) return true;
        else if (filterLevel == LogLevel.INFO) {
            if (lvl == LogLevel.DEBUG) return false;
            else return true;
        } else if (filterLevel == LogLevel.WARNING) {
            if (lvl == LogLevel.DEBUG || lvl == LogLevel.INFO) return false;
            else return true;
        } else if (filterLevel == LogLevel.ERROR) {
            if (lvl == LogLevel.ERROR) return true;
            else return false;
        } else return true;
    }

    /**
     * Write a message to the logger.
     * 
     * @param msg
     *            message text
     * @param lvl
     *            message level
     */
    public void write(boolean isRemote, String msg, LogLevel lvl) {
        String name = this.getName();
        if (filter(lvl)) {
            if (writer == null) writer = new LoggerWriter(this.getLoggerName());

            StringBuilder trace = new StringBuilder();
            try {
                throw new Exception();
            } catch (Exception e) {
                StackTraceElement[] c = e.getStackTrace();
                int min = Math.min(3,
                                   c.length - 1);
                for (int i = min; i >= 1; i--) {
                    trace.append(filterClassName(c[i].getClassName()) + "."
                                 + c[i].getMethodName());
                    if (i > 1) trace.append("->");
                }
            }
            writer.write(lvl.name() + ":" + getSide(isRemote) + ":" + name
                         + ":" + msg + ":" + trace);
        }
    }

    private String getSide(boolean isRemote) {
        if (!isRemote && FMLCommonHandler.instance().getSide() == Side.CLIENT) return "ISERVER";
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) return "CLIENT";
        if (FMLCommonHandler.instance().getSide() == Side.SERVER) return "SERVER";

        return "UNKNOWN";
    }

    /**
     * Write an exception stack trace to the logger.
     * 
     * @param e
     *            exception
     */
    public void writeStackTrace(Exception e) {
        if (writer == null) writer = new LoggerWriter(this.getLoggerName());

        writer.writeStackTrace(e);
        FMLCommonHandler.instance().raiseException(e,
                                                   e.getMessage(),
                                                   false);
    }

    /**
     * Logger file handler.
     * 
     * @author ali4z
     */
    private class LoggerWriter {
        private File        file;
        private FileWriter  fstream;
        private PrintWriter out;

        public LoggerWriter(String modName) {
            try {
                String fileName = SlimevoidLib.proxy.getMinecraftDir()
                                  + File.separator + modName + ".log";
                System.out.println(fileName);
                file = new File(fileName);
                fstream = new FileWriter(file);
                out = new PrintWriter(fstream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void finalize() {
            try {
                out.close();
                fstream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write(String msg) {
            out.write("\n" + System.currentTimeMillis() + ":" + msg);
            out.flush();
        }

        public void writeStackTrace(Exception e) {
            out.write("\n" + System.currentTimeMillis() + ":");
            out.flush();
            e.printStackTrace(out);
            out.flush();
            e.printStackTrace();
        }
    }

    /**
     * Find the class name from a string.<br>
     * Returns the string beyond the last period ".".
     * 
     * @param name
     *            class name
     * @return Filtered class name.
     */

    public static String filterClassName(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * Gets the instance name for the Logger
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the instance name for the Logger
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
}
