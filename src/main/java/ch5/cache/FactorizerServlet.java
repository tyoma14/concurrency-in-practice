package ch5.cache;

import annotation.ThreadSafe;

import javax.servlet.*;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Created by Artyom Zheltyshev on 07.03.2026
 */
@ThreadSafe
public class FactorizerServlet implements Servlet {
    private final Computable<BigInteger, BigInteger[]> cache = new Memoizer<>(new TrialDivisionFactorizer());

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        try {
            BigInteger i = extractFromRequest(req);
            encodeIntoResponse(resp, cache.compute(i));
        } catch (InterruptedException e) {
            encodeError(resp, "factorization interrupted");
        }
    }

    private BigInteger extractFromRequest(ServletRequest req) {
        // mock
        return new BigInteger("41");
    }

    private void encodeIntoResponse(ServletResponse resp, BigInteger[] result) {
        // mock
    }

    private void encodeError(ServletResponse resp, String errorMsg) {
        // mock
    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
