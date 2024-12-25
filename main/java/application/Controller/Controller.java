package application.Controller;

import application.Service.CodeServiceImpl;
import application.entity.Blogs_t;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.ui.Model;
import java.util.Optional;


@org.springframework.stereotype.Controller

public class Controller {
    @Autowired
    CodeServiceImpl service;

    //Permite a exibição dos Dados armazenados no banco no Front end
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getPost() {
        ModelAndView mv = new ModelAndView("Home");
        List<Blogs_t> blogs = service.FindAll();
        mv.addObject("postings", blogs);
        return mv;
    }

    @GetMapping("/Post/{id}")
    public String displayPost(@PathVariable Integer id, Model model) {
        // Busca o post no banco de dados
        Blogs_t blog = service.findById(id);
        if(blog == null) {
            throw new RuntimeException("Post com o id" + id + "não encontrado");
        }
        // Adiciona o blog ao modelo
        model.addAttribute("blog", blog);

        return "Post";
    }
//Realiza a adição de um novo blog
        @PostMapping
        public ResponseEntity<Blogs_t> PostBlog (@RequestBody Blogs_t blogs_T){
            Blogs_t savedBlog = service.save(blogs_T);
            return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
        }
//Deleta um blog existente
        @DeleteMapping(path = "/{id}")
        public ResponseEntity<Optional<Blogs_t>> deleteById (@PathVariable Integer id){
            try {
                service.deleteById(id);
                return new ResponseEntity<Optional<Blogs_t>>(HttpStatus.OK);

            } catch (NoSuchElementException nsee) {
                return new ResponseEntity<Optional<Blogs_t>>(HttpStatus.NOT_FOUND);
            }
        }
//Adiciona um novo Blog
        @PutMapping(path = "/{id}")
        public ResponseEntity<Blogs_t> update (@PathVariable Integer id, @RequestBody Blogs_t blogs_t){
            Blogs_t atualizado = service.updating(id, blogs_t);
            return ResponseEntity.ok(atualizado);
        }
    }