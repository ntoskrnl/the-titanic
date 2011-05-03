package client.util;

import java.io.OutputStream;
import java.io.IOException;

/**
 * This class provides measurement of output data.
 * @author danon
 * @link http://www.thatsjava.com/java-programming/193082/
 */
public class MeasuringOutputStream extends OutputStream {

    private OutputStream underlying_ostream;
    private long count;

    public MeasuringOutputStream(OutputStream out) {
        if (out == null) {
            throw new NullPointerException("Null output stream");
        }
        underlying_ostream = out;
    }

    @Override
    public void close() throws IOException {
        underlying_ostream.close();
    }

    @Override
    public void flush() throws IOException {
        underlying_ostream.flush();
    }

    @Override
    public void write(byte[] b) throws IOException {
        underlying_ostream.write(b);
        count += b.length;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        underlying_ostream.write(b, off, len);
        count += len;
    }

    public void write(int b) throws IOException {
        underlying_ostream.write(b);
        count++;
    }

    public long writtenBytesCount() {
        return count;
    }
}