/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtro;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andre
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"/views/*"})
public class AuthorizationFilter implements Filter {

    public AuthorizationFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String admin = "Administrador";
            String empleado = "Empleado";
            String usuario = "Usuario";
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            /*
            Logica de sesion: 
            NoUsuario: verifica que la url contenga NoUsuario
            Usuario: verifica que la url contenga Usuario
            Admin: verifica que la url contenga backend
            Valores guardados en sesion: Nombre del rol
                                         Atributo idRol
             */
            String reqURI = reqt.getRequestURI();
            boolean loggedIn = (ses != null) && (ses.getAttribute("email") != null);
            if (reqURI.contains("/NoUsuario/") && !loggedIn) {
                chain.doFilter(request, response);
            }else if((ses.getAttribute(admin) != null) && ((reqURI.contains("/Usuario/")) || reqURI.contains("/backend/") )){
                chain.doFilter(request, response);
            } else if ((reqURI.contains("/Usuario/") || reqURI.contains("/backend/")) && !loggedIn) {
                resp.sendRedirect(reqt.getContextPath() + "/views/front-end/NoUsuario/index.xhtml");
            } else if((ses.getAttribute(admin) != null) && !(reqURI.contains("/NoUsuario/"))){
                resp.sendRedirect(reqt.getContextPath() + "/views/front-end/Usuario/inicioUsuario.xhtml");
            }else if((ses.getAttribute(empleado) != null || ses.getAttribute(usuario) != null) && !reqURI.contains("/Usuario/")){
                resp.sendRedirect(reqt.getContextPath() + "/views/front-end/Usuario/inicioUsuario.xhtml");
            }else{
                chain.doFilter(request, response);
            }
            

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }

}
