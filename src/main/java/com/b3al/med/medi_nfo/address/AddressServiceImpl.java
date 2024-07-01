package com.b3al.med.medi_nfo.address;

import com.b3al.med.medi_nfo.user.UserRepository;
import com.b3al.med.medi_nfo.util.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressMapper addressMapper;

    public AddressServiceImpl(final AddressRepository addressRepository,
            final UserRepository userRepository, final AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public Page<AddressDTO> findAll(final String filter, final Pageable pageable) {
        Page<Address> page;
        if (filter != null) {
            Long longFilter = null;
            try {
                longFilter = Long.parseLong(filter);
            } catch (final NumberFormatException numberFormatException) {
                // keep null - no parseable input
            }
            page = addressRepository.findAllById(longFilter, pageable);
        } else {
            page = addressRepository.findAll(pageable);
        }
        return new PageImpl<>(page.getContent()
                .stream()
                .map(address -> addressMapper.updateAddressDTO(address, new AddressDTO()))
                .toList(),
                pageable, page.getTotalElements());
    }

    @Override
    public AddressDTO get(final Long id) {
        return addressRepository.findById(id)
                .map(address -> addressMapper.updateAddressDTO(address, new AddressDTO()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public Long create(final AddressDTO addressDTO) {
        final Address address = new Address();
        addressMapper.updateAddress(addressDTO, address, userRepository);
        return addressRepository.save(address).getId();
    }

    @Override
    public void update(final Long id, final AddressDTO addressDTO) {
        final Address address = addressRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        addressMapper.updateAddress(addressDTO, address, userRepository);
        addressRepository.save(address);
    }

    @Override
    public void delete(final Long id) {
        addressRepository.deleteById(id);
    }

}
