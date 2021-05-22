package com.spring.codeblog.controller;

import com.spring.codeblog.model.Post;
import com.spring.codeblog.service.CodeblogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
public class CodeblogController {

    @Autowired
    CodeblogService codeblogService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView getPosts()
    {
        ModelAndView modelAndView = new ModelAndView("posts");
        List<Post> postList = codeblogService.findAll();
        modelAndView.addObject("posts", postList);
        return  modelAndView;
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public ModelAndView getPostDetails(@PathVariable("id") long id)
    {
        ModelAndView modelAndView = new ModelAndView("postDetails");
        Post post = codeblogService.findById(id);
        modelAndView.addObject("post", post);
        return  modelAndView;
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.GET)
    public String getPostForm()
    {
        return "postForm";
    }

    @RequestMapping(value = "/newPost", method = RequestMethod.POST)
    public String savePost(@Valid Post post, BindingResult result, RedirectAttributes redirectAttributes)
    {
        if(result.hasErrors())
        {
            redirectAttributes.addFlashAttribute("mensagem", "Verifique se os campos obrigatórios foram preenchidos.");
            return "redirect:/newPost";
        }
        post.setData(LocalDate.now());
        codeblogService.save(post);
        return "redirect:/posts";
    }
}
