/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.BuscarDao;
import dao.BuscarDaoImplement;
import dao.CategoriaDao;
import dao.CategoriaDaoImplement;
import dao.ComisionDaoImplement;
import dao.CotizacionDao;
import dao.CotizacionDaoImplement;
import dao.ImagenesDao;
import dao.ImagenesDaoImplement;
import dao.JornadaDaoImplement;
import dao.JornadaDao;
import dao.OfertaDao;
import dao.OfertaDaoImplement;
import dao.PortafolioDao;
import dao.PortafolioDaoImplement;
import dao.RolDao;
import dao.RolDaoImplement;
import dao.TrabajoDao;
import dao.TrabajoDaoImplement;
import dao.UsuarioDao;
import dao.UsuarioDaoImplement;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import model.Categoria;
import model.Comision;
import model.Cotizacion;
import model.Imagenes;
import model.Oferta;
import model.Trabajo;
import model.Usuario;
import model.Jornada;
import model.Portafolio;
import model.Rol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.data.FilterEvent;
import org.primefaces.model.UploadedFile;
import util.MailUtil;

/**
 *
 * @author andre
 */
@ManagedBean(name = "ofertaBean")
@SessionScoped
public class OfertaBean implements Serializable {

    private Logger logger = LogManager.getLogger(OfertaBean.class);
    private List<Oferta> ofertas;
    private List<Oferta> filteredOfertas;
    private String ubicacionFilter = "";
    private Usuario usuarioRegistrado;
    private Integer ofertasCount;
    //Creacion de oferta para pagina ofertar.xhtml
    private Oferta ofertaCreate;
    /**
     * SelectOneMenu Categoria-Trabajo *
     */
    //Trabajos en la base de datos
    private List<Trabajo> trabajos;
    //Trabajos pertenecientes a una categoria
    private List<Trabajo> trabajosTemporal;
    //Categorias en la base de datos
    private List<Categoria> categorias;
    private List<Categoria> subCategoriasTemporal;
    private List<Jornada> jornadas;
    private Integer idCategoria = 0;
    private Integer idSubCategoria = 0;
    private Integer idTrabajo = 0;
    private Integer idJornada = 0;
    private String perfil;
    private String experiencia;
    private String profesion;
    private Date date1;

    //Oferta seleccionada desde datatable
    private Oferta ofertaSelected;

    //Cotizacion
    private Cotizacion cotizacionNueva;
    //Fecha actual
    private Date currentDate = new Date();

    //Search general
    private String ofertaSearchString;

    //Portafolios
    private Portafolio createdPortafolio;
    private List<UploadedFile> uploadFiles = new ArrayList<>();
    //fotos
    private String pathImages = "";
    private String destination = "/images/";
    //Switch
    private boolean portafolioSwitch = false;

    private int idOfertaSelected;

    private String FitroTitulo = "";
    private String FitroUbicacion = "";
    private String FitroJornada = "";
    private String FitroValor = "";

    @PostConstruct
    public void init() {
        cotizacionNueva = new Cotizacion();
        ofertaCreate = new Oferta();
        createdPortafolio = new Portafolio();
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getCurrentInstance().getExternalContext().getContext();
        this.pathImages = servletContext.getRealPath(this.destination);

        //logger.info("Se obtiene la ruta donde se van a despositar y leer imagenes {} ", this.pathImages);
    }

    /**
     * Creates a new instance of OfertaBean
     */
    public OfertaBean() {
        this.trabajos = new ArrayList<>();
        this.categorias = new ArrayList<>();

    }

    public List<UploadedFile> getUploadFiles() {
        return uploadFiles;
    }

    public void setUploadFiles(List<UploadedFile> uploadFiles) {
        this.uploadFiles = uploadFiles;
    }

    public Portafolio getCreatedPortafolio() {
        return createdPortafolio;
    }

    public void setCreatedPortafolio(Portafolio createdPortafolio) {
        this.createdPortafolio = createdPortafolio;
    }

    public Date getCurrentDate() {
        this.currentDate = new Date();
        return currentDate;
    }

    public List<Oferta> getOfertasbyRecientes() {

        //logger.info("Informacion de ofertas recientes");
        OfertaDao linkDao = new OfertaDaoImplement();
        //ofertas = linkDao.findAllbyRecientes();

        ofertas = linkDao.findAllbyRecientes(this.FitroTitulo, this.FitroUbicacion, this.FitroJornada, this.FitroValor);

        //logger.debug("Se listan {}  ofertas ", ofertas.size());
        //limitTable(ofertas, 10);
        return ofertas;
    }

    public void actualizarOferta(){
        OfertaDao od = new OfertaDaoImplement();
        od.actualizarOferta(ofertaSelected);
    }
    public List<Oferta> getOfertasbyCalificcion() {
        OfertaDao linkDao = new OfertaDaoImplement();
        ofertas = linkDao.findAllbyCalificacion();
        //limitTable(ofertas, 10);
        return ofertas;
    }

    public Integer getOfertasCount() {
        OfertaDao linkDao = new OfertaDaoImplement();
        ofertasCount = linkDao.countAll();
        return ofertasCount;

    }

    /**
     *
     * @author andresFVR Limita las filas de la tabla o: lista de ofertas n:
     * TamaÃ±o a limitar
     */
    public void limitTable(List<Oferta> o, Integer n) {
        int sizeOferta = o.size();
        if (sizeOferta > n) {
            o.subList(n, sizeOferta).clear();
        }
    }

    public List<Trabajo> getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(List<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    public List<Trabajo> getTrabajosTemporal() {
        return trabajosTemporal;
    }

    public void setTrabajosTemporal(List<Trabajo> trabajosTemporal) {
        this.trabajosTemporal = trabajosTemporal;
    }

    public List<Categoria> getCategorias() {
        CategoriaDao categoriaDao = new CategoriaDaoImplement();
        this.categorias = categoriaDao.mostrarCategoriasPrincipales();
        return categorias;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdTrabajo() {
        return idTrabajo;
    }

    public void setIdTrabajo(Integer idTrabajo) {
        this.idTrabajo = idTrabajo;
    }

    public Integer getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(Integer idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public List<Categoria> getSubCategoriasTemporal() {
        return subCategoriasTemporal;
    }

    public void setSubCategoriasTemporal(List<Categoria> subCategoriasTemporal) {
        this.subCategoriasTemporal = subCategoriasTemporal;
    }

    public Integer getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Integer idJornada) {
        this.idJornada = idJornada;
    }

    public List<Jornada> getJornadas() {
        JornadaDao jornadaDao = new JornadaDaoImplement();
        this.jornadas = jornadaDao.mostrarJornadas();
        return jornadas;
    }

    public void setJornadas(List<Jornada> jornadas) {
        this.jornadas = jornadas;
    }

    public Usuario getUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    public void setUsuarioRegistrado(Usuario usuarioRegistrado) {
        this.usuarioRegistrado = usuarioRegistrado;
    }

    public Oferta getOfertaSelected() {
        return ofertaSelected;
    }

    public void setOfertaSelected(Oferta ofertaSelected) {
        this.ofertaSelected = ofertaSelected;
    }

    public Cotizacion getCotizacionNueva() {
        return cotizacionNueva;
    }

    public void setCotizacionNueva(Cotizacion cotizacionNueva) {
        this.cotizacionNueva = cotizacionNueva;
    }

    public Oferta getOfertaCreate() {
        return ofertaCreate;
    }

    public void setOfertaCreate(Oferta ofertaCreate) {
        this.ofertaCreate = ofertaCreate;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public List<Oferta> getFilteredOfertas() {
        return filteredOfertas;
    }

    public void setFilteredOfertas(List<Oferta> filteredOfertas) {
        this.filteredOfertas = filteredOfertas;
    }

    public String getUbicacionFilter() {
        return ubicacionFilter;
    }

    public void setUbicacionFilter(String ubicacionFilter) {
        this.ubicacionFilter = ubicacionFilter;
    }

    public String getOfertaSearchString() {
        return ofertaSearchString;
    }

    public void setOfertaSearchString(String ofertaSearchString) {
        this.ofertaSearchString = ofertaSearchString;
    }

    /**
     * SelectOneMenu Categoria return: subcattegoria
     */
    public void handleCategoriaChange() {
        this.idSubCategoria = 0;
        this.idTrabajo = 0;
        //Cargar Dao
        CategoriaDao categoriaDao = new CategoriaDaoImplement();
        //Reiniciar Lista
        this.subCategoriasTemporal = new ArrayList<>();
        if (this.idCategoria != null && this.idCategoria != 0) {
            this.trabajosTemporal = new ArrayList<>();
            this.subCategoriasTemporal = new ArrayList<>();
            this.subCategoriasTemporal = categoriaDao.buscarSubCategoriasByCategoria(idCategoria);
        } else {
            this.subCategoriasTemporal = new ArrayList<>();
            this.trabajosTemporal = new ArrayList<>();
        }
    }

    public void handleSubCategoriaChange() {
        this.idTrabajo = 0;
        TrabajoDao trabajoDao = new TrabajoDaoImplement();
        if (this.idSubCategoria != null && this.idSubCategoria != 0) {
            this.trabajosTemporal = new ArrayList<>();
            this.trabajosTemporal = trabajoDao.buscarTrabajosByCategoriaId(idSubCategoria);
        } else {
            this.trabajosTemporal = new ArrayList<>();
        }
    }

    public void crearOferta() {

        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        //Crear Dao 
        OfertaDao ofertaDao = new OfertaDaoImplement();
        JornadaDao jornadaDao = new JornadaDaoImplement();
        TrabajoDao trabajoDao = new TrabajoDaoImplement();

        //Busqueda y asignacion de informacion 
        Jornada jornada = jornadaDao.buscarJornadabyId(idJornada);
        Trabajo trabajo = trabajoDao.buscarTrabajoById(idTrabajo);

        ofertaCreate.setJornada(jornada);
        ofertaCreate.setTrabajo(trabajo);
        ofertaCreate.setFechaCreacion(new Date());
        ofertaCreate.setEstado(true);
        //Si no tiene rol empleado
        if (!esRol("Empleado", this.usuarioRegistrado)) {

            //Setear Roles y Usuario
            UsuarioDao usuarioDao = new UsuarioDaoImplement();
            RolDao roldaoLink = new RolDaoImplement();
            Rol rolEmpleado = roldaoLink.buscarRolByName("Empleado");
            Set<Rol> rols = new HashSet<Rol>();
            rols.add(rolEmpleado);
            for (Iterator iterator1 = this.usuarioRegistrado.getRols().iterator(); iterator1.hasNext();) {
                Rol rolU = (Rol) iterator1.next();
                rols.add(rolU);
            }

            this.usuarioRegistrado.setRols(rols);
            this.usuarioRegistrado.setPerfil(perfil);
            this.usuarioRegistrado.setExperiencia(experiencia);
            this.usuarioRegistrado.setProfesion(profesion);
            usuarioDao.modificarUsuario(usuarioRegistrado);
        }
        ofertaCreate.setUsuario(this.usuarioRegistrado);

        if (ofertaDao.insertarOferta(ofertaCreate)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excelente", "Oferta enviada!"));
            limpiarCrearOferta();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Oferta no enviada!"));
        }

    }

    public void onRowSelect(SelectEvent se) {
        //Obetener url de redireccion
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String urlRedirectNo = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Detalle_Trabajador.xhtml"));
        String urlRedirectSi = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Detalle_Trabajador.xhtml"));
        //Obtener url actual
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String urlActual = origRequest.getRequestURI();

        try {
            extContext.redirect(urlRedirectSi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoTrabajo(Oferta idOferta) {
        ofertaSelected = idOferta;
        //Obetener url de redireccion
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String urlRedirectNo = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Detalle_Trabajador.xhtml"));
        String urlRedirectSi = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Detalle_Trabajador.xhtml"));
        //Obtener url actual
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String urlActual = origRequest.getRequestURI();

        try {
            extContext.redirect(urlRedirectSi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toCotizar() {
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String urlActual = origRequest.getRequestURI();

        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String urlRedirectNo = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Cotizar.xhtml"));
        String urlRedirectSi = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Cotizar.xhtml"));

        //setear oferta a vista actual
        for (Oferta oferta : ofertas) {
            if (oferta.getIdOferta() == ofertaSelected.getIdOferta()) {
                ofertaSelected = oferta;
            }
        }

        try {
            extContext.redirect(urlRedirectSi);
        } catch (IOException e) {
        }
    }
    public void toCotizar(Oferta laoferta) {
        ofertaSelected= laoferta;
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String urlActual = origRequest.getRequestURI();

        FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String urlRedirectNo = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Cotizar.xhtml"));
        String urlRedirectSi = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/Front/Cotizar.xhtml"));

        //setear oferta a vista actual
        /*for (Oferta oferta : ofertas) {
            if (oferta.getIdOferta() == ofertaSelected.getIdOferta()) {
                ofertaSelected = oferta;
            }
        }*/

        try {
            extContext.redirect(urlRedirectSi);
        } catch (IOException e) {
        }
    }

    /**
     * Comprueba si la oferta es propia Si es mi oferta devuelve
     *
     * @return true
     */
    public Boolean esMiOferta() {
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        if (objetoBean != null) {
            this.usuarioRegistrado = objetoBean.getUsuarioLog();

            if (ofertaSelected.getUsuario().getIdUsuario() == usuarioRegistrado.getIdUsuario()) {
                return true;
            }
        }
        return false;
    }

    public void enviarCotizacion() {

        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();

        CotizacionDao cotizacionDao = new CotizacionDaoImplement();
        cotizacionNueva.setFechaSolicitud(currentDate);
        cotizacionNueva.setOferta(ofertaSelected);

        //Si no tiene rol usuario
        if (!esRol("Usuario", this.usuarioRegistrado)) {
            //Setear Roles y Usuario
            UsuarioDao usuarioDao = new UsuarioDaoImplement();
            RolDao roldaoLink = new RolDaoImplement();
            Rol rolUsuario = roldaoLink.buscarRolByName("Usuario");
            Set<Rol> rols = new HashSet<Rol>();
            rols.add(rolUsuario);
            for (Iterator iterator1 = this.usuarioRegistrado.getRols().iterator(); iterator1.hasNext();) {
                Rol rolU = (Rol) iterator1.next();
                rols.add(rolU);
            }

            this.usuarioRegistrado.setRols(rols);
            usuarioDao.modificarUsuario(usuarioRegistrado);
        }
        cotizacionNueva.setUsuario(usuarioRegistrado);

        ComisionDaoImplement cdi = new ComisionDaoImplement();
        Comision comision = cdi.getComisionAldia();

        cotizacionNueva.setComision(comision);

        if (cotizacionDao.insertarCotizacion(cotizacionNueva)) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excelente", "Cotizacion enviada!"));
            /*MailUtil mi = new MailUtil();
            mi.enviarMail(cotizacionNueva.getOferta().getUsuario().getEmail(), "Ha recibido una oferta de " + cotizacionNueva.getOferta().getUsuario().getNombres(), 
                    "Recibió una oferta de " + cotizacionNueva.getOferta().getUsuario().getNombres() + " Para el trabajo de " + cotizacionNueva.getOferta().getTrabajo().getTitulo() + " para el día "
            + cotizacionNueva.getOferta().getFechaCreacion() + " ingresa ya a la plataforma para aceptar o contraofertar!");*/
            
            cotizacionNueva = new Cotizacion();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Cotizacion no enviada!"));
            System.out.println("Crea cotizacion: falsoooo ");
        }

    }

    public List<Oferta> getOfertasbyUsuario() {
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();

        OfertaDao linkDao = new OfertaDaoImplement();
        ofertas = linkDao.findAllbyIdUsuario(usuarioRegistrado.getIdUsuario());
        //limitTable(ofertas, 10);
        return ofertas;
    }

    public Boolean esRol(String rol, Usuario usuarioRol) {
        for (Iterator iterator1 = usuarioRol.getRols().iterator(); iterator1.hasNext();) {
            Rol rolU = (Rol) iterator1.next();
            if (rolU.getNombre().equals(rol)) {
                return true;
            }
        }
        return false;
    }

    public Boolean esRolLog(String rol) {
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        for (Iterator iterator1 = this.usuarioRegistrado.getRols().iterator(); iterator1.hasNext();) {
            Rol rolU = (Rol) iterator1.next();
            if (rolU.getNombre().equals(rol)) {
                return true;
            }
        }
        return false;
    }

    public Boolean esEmpleado() {
        String rol = "Empleado";
        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        for (Iterator iterator1 = this.usuarioRegistrado.getRols().iterator(); iterator1.hasNext();) {
            Rol rolU = (Rol) iterator1.next();
            if (rolU.getNombre().equals(rol)) {
                return true;
            }
        }
        return false;
    }

    public void limpiarCrearOferta() {
        this.ofertaCreate = new Oferta();
        this.idCategoria = null;
        this.idJornada = null;
        this.idSubCategoria = null;
        this.idTrabajo = null;
        this.createdPortafolio = new Portafolio();
        this.uploadFiles = new ArrayList<>();

    }

    public void filterListener(FilterEvent filterEvent) {
        if (filteredOfertas != null) {
            System.out.println("filterListener: " + filteredOfertas);
            for (Oferta o : filteredOfertas) {
                System.out.println(o.getIdOferta());
            }
        } else {
            System.out.println("filterListener: null ");
        }
    }

    /**
     *
     * @param value : Valor de la tabla
     * @param filter : Valor recibido de la interfaz grafica
     * @param locale : Localizacion
     * @return : Returns a negative integer, zero, or a positive integer as this
     * value is less than, equal to, or greater than the specified filter.
     */
    public boolean filterByCosto(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }

        return ((Comparable) value).compareTo(Integer.valueOf(filterText)) > 0;
    }

    /**
     *
     * @param value : Valor de la tabla
     * @param filter : Valor recibido de la interfaz grafica
     * @param locale : Localizacion
     * @return : Returns a negative integer, zero, or a positive integer as this
     * value is less than, equal to, or greater than the specified filter.
     */
    public boolean filterByJornada(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }
        boolean filtra = filterText.equals(value);

        return filtra;
    }

    /**
     *
     * @param value : Valor de la tabla
     * @param filter : Valor recibido de la interfaz grafica
     * @param locale : Localizacion
     * @return : Returns a negative integer, zero, or a positive integer as this
     * value is less than, equal to, or greater than the specified filter.
     */
    public boolean filterByBarrio(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }
        boolean filtra = filterText.equals(value);

        return filtra;
    }

    /**
     *
     * @param value : Valor de la tabla
     * @param filter : Valor recibido de la interfaz grafica
     * @param locale : Localizacion
     * @return : Returns a negative integer, zero, or a positive integer as this
     * value is less than, equal to, or greater than the specified filter.
     */
    public boolean filterByTituloIgnoreCase(Object value, Object filter, Locale locale) {
        String filterText = (filter == null) ? null : filter.toString().trim();
        if (filterText == null || filterText.equals("")) {
            return true;
        }

        if (value == null) {
            return false;
        }
        boolean filtra = Pattern.compile(Pattern.quote(filterText), Pattern.CASE_INSENSITIVE).matcher((CharSequence) value).find();

        return filtra;
    }

    //Busqueda General
    public List<String> buscarOferta(String query) {

        BuscarDao bdo = new BuscarDaoImplement();
        bdo.almacenarBusqueda(query, usuarioRegistrado);

        OfertaDao oLink = new OfertaDaoImplement();
        List<Oferta> ofertas = new LinkedList<>();
        ofertas = oLink.buscarOfertabyNombre(query);

        List<String> results = new ArrayList<String>();
        for (int i = 0; i < ofertas.size(); i++) {
            results.add(ofertas.get(i).getIdOferta() + " " + ofertas.get(i).getTrabajo().getTitulo() + " " + ofertas.get(i).getTrabajo().getCategoria().getNombre() + " " + ofertas.get(i).getTrabajo().getDescripcion());
        }

        return results;
    }

    public void verOferta() {
        String[] dividido = ofertaSearchString.split(" ");
        String cedula = dividido[0];
        OfertaDao oLink = new OfertaDaoImplement();
        ofertaSelected = oLink.buscarOfertabyId(Integer.valueOf(cedula));
        System.out.println("Oferta seleccionada: " + ofertaSelected);
        System.out.println("Oferta string: " + ofertaSearchString);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("Detalle_Trabajador.xhtml");
        } catch (IOException ex) {
            //   Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarFileToFiles(FileUploadEvent event) {
        uploadFiles.add(event.getFile());
    }

    public boolean isPortafolioSwitch() {
        return portafolioSwitch;
    }

    public void setPortafolioSwitch(boolean portafolioSwitch) {
        this.portafolioSwitch = portafolioSwitch;
    }

    public void crearOfertaPrueba() {

        //Obtener Bean login y acceder al usuarioRegistrado logeado
        FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
        Object loginBean = context.getExternalContext().getSessionMap().get("loginBean");
        LoginBean objetoBean = null;
        if (loginBean != null) {
            objetoBean = (LoginBean) loginBean;
        }
        this.usuarioRegistrado = objetoBean.getUsuarioLog();
        //Crear Dao 
        OfertaDao ofertaDao = new OfertaDaoImplement();
        JornadaDao jornadaDao = new JornadaDaoImplement();
        TrabajoDao trabajoDao = new TrabajoDaoImplement();

        //Busqueda y asignacion de informacion 
        Jornada jornada = jornadaDao.buscarJornadabyId(idJornada);
        Trabajo trabajo = trabajoDao.buscarTrabajoById(idTrabajo);

        ofertaCreate.setJornada(jornada);
        ofertaCreate.setTrabajo(trabajo);
        ofertaCreate.setFechaCreacion(new Date());
        ofertaCreate.setEstado(true);
        //Si no tiene rol empleado
        if (!esRol("Empleado", this.usuarioRegistrado)) {

            //Setear Roles y Usuario
            UsuarioDao usuarioDao = new UsuarioDaoImplement();
            RolDao roldaoLink = new RolDaoImplement();
            Rol rolEmpleado = roldaoLink.buscarRolByName("Empleado");
            Set<Rol> rols = new HashSet<Rol>();
            rols.add(rolEmpleado);
            for (Iterator iterator1 = this.usuarioRegistrado.getRols().iterator(); iterator1.hasNext();) {
                Rol rolU = (Rol) iterator1.next();
                rols.add(rolU);
            }

            this.usuarioRegistrado.setRols(rols);
            this.usuarioRegistrado.setPerfil(perfil);
            this.usuarioRegistrado.setExperiencia(experiencia);
            this.usuarioRegistrado.setProfesion(profesion);
            usuarioDao.modificarUsuario(usuarioRegistrado);
        }
        ofertaCreate.setUsuario(this.usuarioRegistrado);
        //insertar oferta
        if (ofertaDao.insertarOferta(ofertaCreate)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excelente", "Oferta enviada!"));

            //Si se crea portafolio
            if (portafolioSwitch && uploadFiles != null && uploadFiles.size() > 0) {
                this.createdPortafolio.setEstado(true);
                this.createdPortafolio.setFechaCreacion(new Date());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Con portafolio"));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Estado", "" + this.createdPortafolio.isEstado()));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Descripcion", "" + this.createdPortafolio.getDescripcion()));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fecha creacion", "" + this.createdPortafolio.getFechaCreacion()));

                createdPortafolio.setOferta(ofertaCreate);
                ofertaCreate.getPortafolios().add(createdPortafolio);
                //Guardar Portafolio
                PortafolioDao portafolioLink = new PortafolioDaoImplement();
                if (portafolioLink.insertarPortafolio(createdPortafolio)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Excelente", "Portafolio guardado!"));

                    String nombreImagen = "";
                    int i = 1;
                    //Crear imagenes y subirlas al server
                    ImagenesDao imagenesLink = new ImagenesDaoImplement();
                    //Asignar nombres de las imagenes
                    for (UploadedFile u : uploadFiles) {
                        String nombre = u.getFileName();
                        String extencion = nombre.substring(nombre.indexOf(".") + 1);
                        nombreImagen = "Potafolio-" + createdPortafolio.getIdPortafolio() + "-" + i + "." + extencion;
                        Imagenes imagen = new Imagenes(createdPortafolio, nombreImagen, true);
                        createdPortafolio.getImageneses().add(imagen);

                        //Guardar Imagen
                        try {
                            TransferFile(nombreImagen, u.getInputstream(), u);
                            //Guardar Imagen
                            imagenesLink.insertarImagen(imagen);
                            FacesContext context2 = FacesContext.getCurrentInstance();
                            context2.addMessage(null, new FacesMessage("Bien!", "Se subio correctamente la foto"));
                        } catch (IOException ex) {
                            FacesContext context3 = FacesContext.getCurrentInstance();
                            context3.addMessage(null, new FacesMessage("Error", "Error subiendo la foto"));
                        }

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Foto", "" + u.getFileName()));
                        i++;
                    }

                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Sin portafolio"));
            }

            limpiarCrearOferta();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Oferta no enviada!"));
        }

    }

    public void TransferFile(String fileName, InputStream in, UploadedFile uploadFile) {
        try {
            OutputStream out = new FileOutputStream(new File(this.pathImages + fileName));
            int reader = 0;
            byte[] bytes = new byte[(int) uploadFile.getSize()];
            while ((reader = in.read(bytes)) != -1) {
                out.write(bytes, 0, reader);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getIdOfertaSelected() {
        return idOfertaSelected;
    }

    public void setIdOfertaSelected(int idOfertaSelected) {
        this.idOfertaSelected = idOfertaSelected;
    }

    public String getFitroTitulo() {
        return FitroTitulo;
    }

    public void setFitroTitulo(String FitroTitulo) {
        this.FitroTitulo = FitroTitulo;
    }

    public String getFitroUbicacion() {
        return FitroUbicacion;
    }

    public void setFitroUbicacion(String FitroUbicacion) {
        this.FitroUbicacion = FitroUbicacion;
    }

    public String getFitroJornada() {
        return FitroJornada;
    }

    public void setFitroJornada(String FitroJornada) {
        this.FitroJornada = FitroJornada;
    }

    public String getFitroValor() {
        return FitroValor;
    }

    public void setFitroValor(String FitroValor) {
        this.FitroValor = FitroValor;
    }
}
