package com.devsu.hackerearth.backend.client.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	public ResponseEntity<List<ClientDto>> getAll() {
		// api/clients
		// Get all clients
		return ResponseEntity.ok(clientService.getAll());
	}

	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		// api/clients/{id}
		// Get clients by id
		ClientDto result = clientService.getById(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
		// api/clients
		// Create client
		return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientDto));
	}

	public ResponseEntity<ClientDto> update(@PathVariable Long id, @RequestBody ClientDto clientDto) {
		// api/clients/{id}
		// Update client
		ClientDto result = clientService.getById(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		result.setName(clientDto.getName());
		result.setDni(clientDto.getDni());
		result.setGender(clientDto.getGender());
		result.setAge(clientDto.getAge());
		result.setAddress(clientDto.getAddress());
		result.setActive(clientDto.isActive());
		result.setPassword(clientDto.getPassword());
		result.setPhone(clientDto.getPhone());
		ClientDto clientResult = clientService.update(result);
		if (clientResult == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(clientResult);
	}

	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {
		// api/accounts/{id}
		// Partial update accounts
		ClientDto result = clientService.partialUpdate(id, partialClientDto);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(result);
	}

	public ResponseEntity<Void> delete(@PathVariable Long id) {
		// api/clients/{id}
		// Delete client
		ClientDto result = clientService.getById(id);
		if (result == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		clientService.deleteById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
