package com.company.enroller.controllers;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;




	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants(
			@RequestParam(value = "sortOrder", defaultValue = "") String order,
			@RequestParam(value = "key", defaultValue = "") String filter) {

		Collection<Participant> participants = participantService.getAll(order, filter);
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}

//	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
//	public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
//		Participant participant = participantService.findByLogin(login);
//		if (participant == null) {
//			return new ResponseEntity(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
//	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> addParticipant(@RequestBody Participant participant) {
		if (participantService.findByLogin(participant.getLogin()) != null) {
			return new ResponseEntity<String>(
					"Unable to create. A participant with login " + participant.getLogin() + " already exist.",
					HttpStatus.CONFLICT);
		}
		participantService.add(participant);
		return new ResponseEntity<Participant>(participant, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		participantService.delete(participant);
		return new ResponseEntity<Participant>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") String login, @RequestBody Participant updatedParticipant) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		participant.setPassword(updatedParticipant.getPassword());
		participantService.update(participant);
		return new ResponseEntity<Participant>(HttpStatus.OK);
	}




	//	@GetMapping
//	public ResponseEntity<?> sortParticipants() {
//		Collection<Participant> participants = participantService.getAll();
//		List<Participant> sortedList = participants.stream()
//				.sorted(Comparator.comparing(Participant::getLogin))
//				.collect(Collectors.toList());
//
//		return new ResponseEntity<>(sortedList, HttpStatus.OK);
//	}

//	@GetMapping("/participants")
//	public ResponseEntity<?> sortParticipants(
//			@RequestParam(defaultValue = "login") String sortBy,
//			@RequestParam(defaultValue = "ASC") String sortOrder) {
//
//		Collection<Participant> participants = participantService.getAll();
//		List<Participant> sortedList = new ArrayList<>(participants);
//
//		Comparator<Participant> comparator;
//
//		switch (sortBy.toLowerCase()) {
//			case "login":
//				comparator = Comparator.comparing(Participant::getLogin);
//				break;
//			default:
//				return new ResponseEntity<>("Nieobsługiwane pole sortowania: " + sortBy, HttpStatus.BAD_REQUEST);
//		}
//
//		if ("desc".equalsIgnoreCase(sortOrder)) {
//			comparator = comparator.reversed();
//		}
//
//		sortedList.sort(comparator);
//
//		return new ResponseEntity<>(sortedList, HttpStatus.OK);
//	}
//
//	@GetMapping("/participants")
//	public ResponseEntity<?> filterParticipants(
//			@RequestParam(required = false) String key) {
//
//		Collection<Participant> participants = participantService.getAll();
//
//
//		Stream<Participant> stream = participants.stream();
//		if (key != null && !key.isEmpty()) {
//			stream = stream.filter(p -> p.getLogin() != null && p.getLogin().toLowerCase().contains(key.toLowerCase()));
//		}
//
//		List<Participant> filteredList = stream.collect(Collectors.toList());
//
//		return new ResponseEntity<>(filteredList, HttpStatus.OK);
//	}
//
//	@GetMapping("/participants")
//	public ResponseEntity<?> getParticipants(
//			@RequestParam(required = false) String key,
//			@RequestParam(defaultValue = "login") String sortBy,
//			@RequestParam(defaultValue = "ASC") String sortOrder) {
//
//		Collection<Participant> participants = participantService.getAll();
//
//		// Filtrowanie po loginie
//		Stream<Participant> stream = participants.stream();
//		if (key != null && !key.isEmpty()) {
//			stream = stream.filter(p -> p.getLogin() != null && p.getLogin().toLowerCase().contains(key.toLowerCase()));
//		}
//
//		// Sortowanie
//		Comparator<Participant> comparator;
//		switch (sortBy.toLowerCase()) {
//			case "login":
//				comparator = Comparator.comparing(Participant::getLogin, Comparator.nullsLast(String::compareToIgnoreCase));
//				break;
//			default:
//				return new ResponseEntity<>("Nieobsługiwane pole sortowania: " + sortBy, HttpStatus.BAD_REQUEST);
//		}
//
//		if ("desc".equalsIgnoreCase(sortOrder)) {
//			comparator = comparator.reversed();
//		}
//
//		List<Participant> result = stream.sorted(comparator).collect(Collectors.toList());
//
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}
//


}
