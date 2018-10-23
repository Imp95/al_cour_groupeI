<?php
// src/Entity/Ad.php
namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\OneToMany;
use Doctrine\ORM\Mapping\ManyToOne;
use Doctrine\ORM\Mapping\JoinColumn;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity
 */
class Ad
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="date", nullable=false)
     */
    private $departureDate;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="date", nullable=false)
     */
    private $arrivalDate;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $bagage;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="integer", nullable=false)
     */
    private $payment;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="integer", nullable=false)
     */
    private $status;

    /**
     * @ManyToOne(targetEntity="User", inversedBy="ads")
     * @JoinColumn(name="client_id", referencedColumnName="id")
     */
    private $client;

    /**
     * @ORM\Column(type="text", nullable=false)
     */
    private $departureAddress;

    /**
     * @ORM\Column(type="text", nullable=false)
     */
    private $arrivalAddress;

    /**
     * @OneToMany(targetEntity="Offer", mappedBy="ad")
     */
    private $offers;

    public function __construct() {
        $this->offers = new ArrayCollection();
    }

    public function getId()
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getDepartureDate()
    {
        return $this->departureDate;
    }

    /**
     * @param mixed $departureDate
     */
    public function setDepartureDate($departureDate): void
    {
        $this->departureDate = $departureDate;
    }

    /**
     * @return mixed
     */
    public function getArrivalDate()
    {
        return $this->arrivalDate;
    }

    /**
     * @param mixed $arrivalDate
     */
    public function setArrivalDate($arrivalDate): void
    {
        $this->arrivalDate = $arrivalDate;
    }

    /**
     * @return mixed
     */
    public function getBagage()
    {
        return $this->bagage;
    }

    /**
     * @param mixed $bagage
     */
    public function setBagage($bagage): void
    {
        $this->bagage = $bagage;
    }

    /**
     * @return mixed
     */
    public function getPayment()
    {
        return $this->payment;
    }

    /**
     * @param mixed $payment
     */
    public function setPayment($payment): void
    {
        $this->payment = $payment;
    }

    /**
     * @return mixed
     */
    public function getStatus()
    {
        return $this->status;
    }

    /**
     * @param mixed $status
     */
    public function setStatus($status): void
    {
        $this->status = $status;
    }

    /**
     * @return mixed
     */
    public function getClient()
    {
        return $this->client;
    }

    /**
     * @param mixed $client
     */
    public function setClient($client): void
    {
        $this->client = $client;
    }

    /**
     * @return mixed
     */
    public function getDepartureAddress()
    {
        return $this->departureAddress;
    }

    /**
     * @param mixed $departureAddress
     */
    public function setDepartureAddress($departureAddress): void
    {
        $this->departureAddress = $departureAddress;
    }

    /**
     * @return mixed
     */
    public function getArrivalAddress()
    {
        return $this->arrivalAddress;
    }

    /**
     * @param mixed $arrivalAddress
     */
    public function setArrivalAddress($arrivalAddress): void
    {
        $this->arrivalAddress = $arrivalAddress;
    }

    /**
     * @return Collection|Offer[]
     */
    public function getOffers(): Collection
    {
        return $this->offers;
    }

    /**
     * @param mixed $offer
     */
    public function addOffer($offer): void
    {
        $this->offers->add($offer);
    }

    /**
     * @param mixed $offer
     */
    public function removeOffer($offer): void
    {
        $this->offers->removeElement($offer);
    }

}