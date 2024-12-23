package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;
import com.devsu.hackerearth.backend.client.model.*;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		// Get all clients
		List<ClientDto> clients = clientRepository
				.findAll().stream().map(e -> clientToDto(e))
				.collect(Collectors.toList());
		return clients;
	}

	@Override
	public ClientDto getById(Long id) {
		// Get clients by id
		Optional<Client> optional = clientRepository.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		return clientToDto(optional.get());
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		// Create client

		clientRepository.save(dtoToClient(clientDto));
		return clientToDto(clientRepository.save(dtoToClient(clientDto)));
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		// Update client
		Optional<Client> optional = clientRepository.findById(clientDto.getId());
		Client clientToUpdate = optional.get();
		clientToUpdate.setName(clientDto.getName());
		clientToUpdate.setDni(clientDto.getDni());
		clientToUpdate.setGender(clientDto.getGender());
		clientToUpdate.setAge(clientDto.getAge());
		clientToUpdate.setAddress(clientDto.getAddress());
		clientToUpdate.setActive(clientDto.isActive());
		clientToUpdate.setPassword(clientDto.getPassword());
		clientToUpdate.setPhone(clientDto.getPhone());
		Client result = clientRepository.save(clientToUpdate);
		return clientToDto(result);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		// Partial update account
		Optional<Client> optional = clientRepository.findById(id);
		if (optional.isEmpty()) {
			return null;
		}
		Client client = optional.get();
		client.setActive(partialClientDto.isActive());
		return clientToDto(clientRepository.save(client));
	}

	@Override
	public void deleteById(Long id) {
		// Delete client
		Optional<Client> optional = clientRepository.findById(id);
		clientRepository.delete(optional.get());
	}

	private ClientDto clientToDto(Client client) {
		return new ClientDto(
				client.getId(),
				client.getDni(),
				client.getName(),
				client.getPassword(),
				client.getGender(),
				client.getAge(),
				client.getAddress(),
				client.getPhone(),
				client.isActive());
	}

	private Client dtoToClient(ClientDto dto) {
		Client client = new Client();
		client.setId(dto.getId());
		client.setDni(dto.getDni());
		client.setName(dto.getName());
		client.setGender(dto.getGender());
		client.setAge(dto.getAge());
		client.setAddress(dto.getAddress());
		client.setActive(dto.isActive());
		client.setPassword(dto.getPassword());
		client.setPhone(dto.getPhone());
		return client;
	}
}
