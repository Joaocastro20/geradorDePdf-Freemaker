package aula.com.projeto.controller;


import aula.com.projeto.exception.GeracaoDocumentoException;
import aula.com.projeto.model.TemplateDocumento;
import aula.com.projeto.model.User;
import aula.com.projeto.service.GeradorPdfService;
import aula.com.projeto.service.TemplateDocumentoService;
import aula.com.projeto.service.UserService;
import freemarker.template.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    GeradorPdfService geradorPdfService;

    @Autowired
    TemplateDocumentoService templateDocumentoService;

    private final Configuration configuration;

    public IndexController(Configuration configuration) {
        this.configuration = configuration;
    }

    @GetMapping("test/{id}")
    public ModelAndView get(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("listarid");
        String user = userService.findById(id).getName();
        mv.addObject("usuario", user);
        return mv;

    }
    @GetMapping("/createpdf")
    public ModelAndView get(){
        ModelAndView mv = new ModelAndView("gerarpdf");
        List<User> user = userService.findAll().stream().collect(Collectors.toList());
        mv.addObject("user", user);
        return mv;
    }
    @GetMapping("/template")
    public ModelAndView getTemplate(){
        ModelAndView mv = new ModelAndView("templateteste");
        String nome = userService.findById(1).getName();
        String data = DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
        mv.addObject("nome", nome)
            .addObject("data", data);
        return mv;
    }
    @GetMapping("/createpdf/{id}/{idt}")
    public String pdfCreate(@PathVariable("id")long id,@PathVariable("idt") long idt) throws GeracaoDocumentoException, IOException {
        User user = userService.findById(id);
        TemplateDocumento templateDocumento = templateDocumentoService.findById(idt);
        final String templatepro = IOUtils.toString(geradorPdfService.prossesaTemplate(user, templateDocumento));
        Reader template = new StringReader(templatepro);
        System.out.println(templatepro);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        geradorPdfService.gerarPDF(template, stream);
        final byte[] pdfData = stream.toByteArray();
        System.out.println(Arrays.toString(pdfData));
        Path temp = Files.createTempFile("hello", ".pdf");
        System.out.println(temp);
        FileUtils.writeByteArrayToFile(temp.toFile(), pdfData);
        stream.close();
        return "templateteste";
    }
}
