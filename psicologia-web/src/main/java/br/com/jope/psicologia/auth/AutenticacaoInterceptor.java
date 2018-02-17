package br.com.jope.psicologia.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import br.com.jope.psicologia.enumeration.EnumUsuario;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.vo.UsuarioVO;

public class AutenticacaoInterceptor extends HandlerInterceptorAdapter {

	private static final String URL_LOGIN = "/auth";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UsuarioVO usuarioLogado = (UsuarioVO) request.getSession().getAttribute(EnumUsuario.USUARIO_LOGADO.getDescricao());
		
		String uri = request.getRequestURI();
        if(uri.endsWith(URL_LOGIN) || uri.contains("resources")){
            return true;
        }

        if(!Util.isEmpty(usuarioLogado)) {
            return true;
        }

        response.sendRedirect(request.getServletContext().getContextPath() + URL_LOGIN);
        return false;
	}
	
}
