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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class LogsRootProbeTest {

    @AfterEach
    void clearProperty() {
        System.clearProperty(SafeTimerTask.LOGS_ROOT_PATH_PROPERTY);
    }

    @Test
    void trimmedCustomLogsRootPathReturnsNullWhenPropertyAbsent() {
        System.clearProperty(SafeTimerTask.LOGS_ROOT_PATH_PROPERTY);
        assertNull(LogsRootProbe.trimmedCustomLogsRootPath());
    }

    @Test
    void trimmedCustomLogsRootPathTrimsWhitespace() {
        System.setProperty(SafeTimerTask.LOGS_ROOT_PATH_PROPERTY, "  /tmp/custom-logs  ");
        assertTrue(LogsRootProbe.trimmedCustomLogsRootPath().equals("/tmp/custom-logs"));
    }

    @Test
    void isCustomLogsRootWritableReturnsFalseWhenPropertyAbsent() {
        System.clearProperty(SafeTimerTask.LOGS_ROOT_PATH_PROPERTY);
        assertFalse(LogsRootProbe.isCustomLogsRootWritable());
    }

    @Test
    void isCustomLogsRootWritableReturnsFalseWhenDirectoryDoesNotExist(@TempDir File tempDir) {
        File missing = new File(tempDir, "does-not-exist");
        System.setProperty(SafeTimerTask.LOGS_ROOT_PATH_PROPERTY, missing.getAbsolutePath());
        assertFalse(LogsRootProbe.isCustomLogsRootWritable());
    }

    @Test
    void isCustomLogsRootWritableReturnsTrueForWritableDirectory(@TempDir File tempDir) {
        System.setProperty(SafeTimerTask.LOGS_ROOT_PATH_PROPERTY, tempDir.getAbsolutePath());
        assertTrue(LogsRootProbe.isCustomLogsRootWritable());
    }
}
