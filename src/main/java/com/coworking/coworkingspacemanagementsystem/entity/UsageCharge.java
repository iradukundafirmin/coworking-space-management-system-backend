package com.coworking.coworkingspacemanagementsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "usage_charge")
public class UsageCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String chargeName;

    private String notes;

    private int amount;

    private LocalDate chargedDate;

    @ManyToOne
    @JoinColumn(name = "desk_booking_id")
    private DeskBooking deskBooking;

    public UsageCharge() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getChargedDate() {
        return chargedDate;
    }

    public void setChargedDate(LocalDate chargedDate) {
        this.chargedDate = chargedDate;
    }

    public DeskBooking getDeskBooking() {
        return deskBooking;
    }

    public void setDeskBooking(DeskBooking deskBooking) {
        this.deskBooking = deskBooking;
    }

}