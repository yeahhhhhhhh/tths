package cn.edu.cqupt.scie.tths.filter;

import cn.edu.cqupt.scie.tths.constant.StatusCodeConstant;
import cn.edu.cqupt.scie.tths.model.UserModel;
import cn.edu.cqupt.scie.tths.model.json.ResponseJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by why on 2017/3/26.
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        int uid;
        int sessionUid;
        String uidStr = req.getParameter("uid");
        if(req.getSession().getAttribute("uid") != null && uidStr != null && !"".equals(uidStr)){
            sessionUid = (int) req.getSession().getAttribute("uid");
            uid = Integer.parseInt(uidStr);
            if(uid == sessionUid && uid != 0){
                chain.doFilter(request,response);
                return;
            }
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseJson responseJson = new ResponseJson(StatusCodeConstant.USER_UNLOGIN);
            resp.setContentType("text/html;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(responseJson));
        }


    }

    @Override
    public void destroy() {

    }
}
