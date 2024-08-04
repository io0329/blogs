package com.example.log4shell;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "login", urlPatterns = {"/login"})
public class Login extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(Login.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String output;

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (email == null || password == null) {
                out.println("이메일 또는 비밀번호가 누락되었습니다.");
                logger.error("요청에 이메일 또는 비밀번호가 누락되었습니다.");
                return;
            }
            out.println("안녕하세요 " + email + "!!");
            out.println("비밀번호는 " + password+"입니다.");

            if (email.startsWith("msglookup@")) {
                out.println("모드: 메시지 조회");
                output = "메시지 조회: " + sanitize(password);
            } else if (email.startsWith("threadcontext@")) {
                ThreadContext.put("apiversion", password);
                out.println("모드: 스레드 컨텍스트");
                output = "메시지 조회 없음! 대신 스레드 컨텍스트!";
            } else {
                out.println("모드: 기본 메시지 조회");
                output = "기본 메시지 조회: " + sanitize(password);
            }

            logger.error(output);
        }
    }

    // 민감한 정보를 로그에 남기지 않도록 처리하는 유틸리티 메소드
    private String sanitize(String input) {
        // 필요한 경우 민감한 정보를 제거하는 로직을 추가
        return input.replaceAll("[\r\n]", "");
    }
}
