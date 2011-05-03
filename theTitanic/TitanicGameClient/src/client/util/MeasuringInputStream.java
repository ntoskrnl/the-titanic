package client.util;

import java.io.InputStream;
import java.io.IOException;

/**
 * This class provides measurement of input data.
 * @author danon
 * @link http://www.thatsjava.com/java-programming/193082/
 */
public class MeasuringInputStream extends InputStream {
    private InputStream underlying_istream;
    private long count;

    public MeasuringInputStream(InputStream in) {
        if (in == null) {
            throw new NullPointerException("Null input stream");
        }
        underlying_istream = in;
    }

    @Override
    public int available() throws IOException {
        return underlying_istream.available();
    }

    @Override
    public void close() throws IOException {
        underlying_istream.close();
    }

    @Override
    public void mark(int readlimit) {
        underlying_istream.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return underlying_istream.markSupported();
    }

    public int read() throws IOException {
        int b = underlying_istream.read();
        if (b != -1) {
            count++;
        }
        return b;

    }

    @Override
    public int read(byte[] b) throws IOException {
        int result = underlying_istream.read(b);
        if (result != -1) {
            count += result;
        }
        return result;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = underlying_istream.read(b, off, len);
        if (result != -1) {
            count += result;
        }
        return result;

    }
    
    public long readBytesCount() {
        return count;
    }

    @Override
    public void reset() throws IOException {
        underlying_istream.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return underlying_istream.skip(n);
    }
}
