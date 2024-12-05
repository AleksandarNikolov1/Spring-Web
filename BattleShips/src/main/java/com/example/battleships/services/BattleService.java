package com.example.battleships.services;

import com.example.battleships.models.dtos.StartBattleDTO;
import com.example.battleships.models.entities.Ship;
import com.example.battleships.repositories.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BattleService {
    private final ShipRepository shipRepository;

    public BattleService(ShipRepository shipRepository) {
        this.shipRepository = shipRepository;
    }

    public void attack(StartBattleDTO attackData) {
        Optional<Ship> attackerOpt = shipRepository.findById(attackData.getAttackerId());
        Optional<Ship> defenderOpt = shipRepository.findById(attackData.getDefenderId());

        if (attackerOpt.isEmpty() || defenderOpt.isEmpty()) {
            throw new NoSuchElementException();
        }

        Ship attacker = attackerOpt.get();
        Ship defender = defenderOpt.get();

        long newDefenderHealth = defender.getHealth() - attacker.getPower();

        if (newDefenderHealth <= 0) {
            shipRepository.delete(defender);
        }

        else {
            defender.setHealth(newDefenderHealth);
            shipRepository.save(defender);
        }
    }
}
