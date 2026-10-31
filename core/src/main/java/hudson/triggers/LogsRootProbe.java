/*
 * The MIT License
 *
 * Copyright (c) 2004-2009, Sun Microsystems, Inc., Kohsuke Kawaguchi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package hudson.triggers;

import java.io.File;
import jenkins.util.SystemProperties;

/**
 * Helper used to probe Qodo's push-triggered re-review behavior on a draft PR.
 * Null-safe trimmed-path accessor plus a writability check for the configured
 * custom logs-root directory.
 */
public final class LogsRootProbe {

    private LogsRootProbe() {}

    /**
     * Returns the trimmed value of the custom logs-root property, or {@code null}
     * if the property is unset.
     */
    public static String trimmedCustomLogsRootPath() {
        String value = SystemProperties.getString(SafeTimerTask.LOGS_ROOT_PATH_PROPERTY);
        return value == null ? null : value.trim();
    }

    /**
     * Whether the configured custom logs-root directory exists and is writable.
     * Returns {@code false} (rather than throwing) when no custom logs-root is
     * configured, the path is blank, or the directory does not exist or isn't
     * writable — callers should treat all of those as "not usable."
     */
    public static boolean isCustomLogsRootWritable() {
        String path = trimmedCustomLogsRootPath();
        if (path == null || path.isEmpty()) {
            return false;
        }
        File dir = new File(path);
        return dir.isDirectory() && dir.canWrite();
    }
}
