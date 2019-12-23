package com.geekbrains.server.controllers;

import com.geekbrains.gwt.common.ItemDto;
import com.geekbrains.server.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
//@CrossOrigin
public class MainController {
    private ItemService itemService;

    @Autowired
    public MainController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/simple")
    public ItemDto simple() {
        return new ItemDto(1L, "Item");
    }

    @GetMapping("/items")
    public List<ItemDto> getAllItems() {
        List<ItemDto> items = itemService.findAll();
        return items;
    }

    @GetMapping("/items/remove/{id}")
    public ResponseEntity<String> removeItems(@PathVariable Long id) {
        itemService.remove(id);
        return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
    }

    @PostMapping("/items")
    public ItemDto createNewItem(@RequestParam Long id, @RequestParam String title) {
        return itemService.save(new ItemDto(id, title));
    }
}