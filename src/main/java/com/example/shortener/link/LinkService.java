package com.example.shortener.link;

import com.example.shortener.link.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LinkService {
    private final LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Transactional
    public LinkDto shortenLink(LinkCreateDto link) {
        String randomId;
        do {
            randomId = UUIDRandomIdGenerator.generateId();
        } while (linkRepository.existsById(randomId));
        Link newLink = new Link(randomId, link.getName(), link.getPassword(), link.getTargetUrl());
        Link savedLink = linkRepository.save(newLink);
        return LinkDtoMapper.map(savedLink);
    }

    @Transactional
    public Optional<LinkDto> incrementVisitsById(String id) {
        Optional<Link> link = linkRepository.findById(id);
        link.ifPresent(l -> l.setVisits(l.getVisits() + 1));
        return link.map(LinkDtoMapper::map);
    }

    public Optional<LinkDto> findLinkById(String id) {
        return linkRepository.findById(id)
                .map(LinkDtoMapper::map);
    }

    @Transactional
    public void updateLink(String linkId, LinkUpdateDto linkUpdateDto){
        Optional<Link> linkToUpdate = linkRepository.findById(linkId);
        linkToUpdate.orElseThrow(LinkNotFoundException::new);
        linkToUpdate.filter(entity -> checkPassword(entity, linkUpdateDto.getPassword()))
                .orElseThrow(InvalidPasswordException::new)
                .setName(linkUpdateDto.getName());
        // możliwa aktualizacja jedynie nazwy linka
        // Aktualizacja  wyłącznie w przypadku linków, które maja ustawione hasło
    }

    private boolean checkPassword(Link entity, String password) {
        return entity.getPassword() != null && entity.getPassword().equals(password);
    }

    public void deleteById(String id, String password){
        Optional<Link> linkById = linkRepository.findById(id);
        if (linkById.isPresent()){
            Link linkToDelete = linkById.filter(link -> checkPassword(link, password))
                    .orElseThrow(InvalidPasswordException::new);
            linkRepository.delete(linkToDelete);
        }
    }


}
