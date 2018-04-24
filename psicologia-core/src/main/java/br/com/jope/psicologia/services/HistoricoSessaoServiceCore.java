package br.com.jope.psicologia.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.entity.HistoricoSessao;
import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.vo.ConsultaVO;

@Service("historicoSessaoService")
@Transactional
public class HistoricoSessaoServiceCore extends BaseServiceCore<HistoricoSessao> implements HistoricoSessaoService {

	private static final long serialVersionUID = -7476146899808883851L;
	private static Logger logger = Logger.getLogger(HistoricoSessaoServiceCore.class.getName());

	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired(required=true)
	@Qualifier("sessaoService")
	private SessaoService sessaoService;
	
	@Autowired(required=true)
	@Qualifier("medicoService")
	private MedicoService medicoService;
	
	@Autowired(required=true)
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@Override
	protected Class<HistoricoSessao> getBeanClass() {
		return HistoricoSessao.class;
	}

	public void incluirHistoricoSessao(Long nuSessao, Long nuUsuario, String tipoUsuario, boolean inicio) throws BussinessException {
		try {
			synchronized(this) {
				ConsultaVO consulta = new ConsultaVO(HistoricoSessao.FIND_HISTORICO_SESSAO);
				consulta.addParametros("nuSessao", nuSessao);
				
				List<HistoricoSessao> historicoSessaoList = loadListNamedQuery(consulta);
				
				boolean update = false;
				boolean insert = false;
				HistoricoSessao historicoSessao = null;
				if(!Util.isEmpty(historicoSessaoList)) {
					update = true;
					historicoSessao = historicoSessaoList.get(0);
				}else {
					insert = true;
					Sessao sessao = sessaoService.getId(Sessao.class, nuSessao);
					
					historicoSessao = new HistoricoSessao();
					historicoSessao.setDhRegistro(new Date());
					historicoSessao.setSessao(sessao);
				}
				
				if(inicio) {
					if(tipoUsuario.equals("psi_pac")) {
						Medico medico = medicoService.loadMedicoPorUsuario(nuUsuario);
						historicoSessao.setMedico(medico);
						historicoSessao.setDhInicioMedico(new Date());
					}else if(tipoUsuario.equals("pac_psi")) {
						Cliente cliente = clienteService.loadClientePorUsuario(nuUsuario);
						historicoSessao.setCliente(cliente);
						historicoSessao.setDhInicioCliente(new Date());
					}				
				}else if(update){
					Usuario usuario = usuarioService.getId(Usuario.class, nuUsuario);
					historicoSessao.setUsuario(usuario);
					historicoSessao.setDhEncerramento(new Date());
				}
				
				if(update) {
					super.alterar(historicoSessao);
				}else if(inicio && insert) {
					super.incluir(historicoSessao);
				}
			}
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMensagem());
		}
	}
	
}
