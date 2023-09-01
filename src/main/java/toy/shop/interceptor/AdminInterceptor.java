package toy.shop.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import toy.shop.entity.dto.SessionMemberDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        HttpSession session = request.getSession();
        SessionMemberDto member = (SessionMemberDto) session.getAttribute("member");
        if (member == null || !member.getAdminChk()) {
            response.sendRedirect("/main");
            return false;
        }
        return true;
    }
}
