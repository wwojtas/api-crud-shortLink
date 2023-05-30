package com.example.shortener.link;

import com.example.shortener.link.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/links")
class LinkController {
    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    ResponseEntity<LinkDto> save(@RequestBody LinkCreateDto link) {
        LinkDto linkDto = linkService.shortenLink(link);
        URI savedEntityLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(linkDto.getId())
                .toUri();
        return ResponseEntity.created(savedEntityLocation).body(linkDto);
    }

    @GetMapping("/{id}")
    ResponseEntity<LinkDto> findById(@PathVariable String id) {
        return linkService.findLinkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> update(@PathVariable String id, @RequestBody LinkUpdateDto linkUpdateDto){
        try {
            linkService.updateLink(id, linkUpdateDto);
            return ResponseEntity.noContent().build();
        } catch (LinkNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (InvalidPasswordException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("reason", e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteById(@PathVariable String id, @RequestHeader String password){
        try{
            linkService.deleteById(id, password);
            return ResponseEntity.noContent().build();
        } catch (InvalidPasswordException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .header("reason", e.getMessage())
                    .build();
        }
    }

}
