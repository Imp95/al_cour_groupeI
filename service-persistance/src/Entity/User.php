<?php
// src/Entity/User.php
namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\OneToMany;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity
 */
class User
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @Assert\NotBlank()
     * @Assert\Email(
     *     message = "The email '{{ value }}' is not a valid email.",
     *     checkMX = true
     * )
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $email;

    /**
     * @Assert\NotBlank()
     * @Assert\Regex("/^([A-Za-z]+| |-)$/")
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $name;

    /**
     * @Assert\NotBlank()
     * @Assert\Regex("/^([A-Za-z]+| |-)$/")
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $firstname;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="date", nullable=false)
     */
    private $birtiday;

    /**
     * @Assert\NotBlank()
     * @Assert\Regex("/^(?:(?:\+|00)33|0)\s*[1-9](?:[\s.-]*\d{2}){4}$/")
     * @ORM\Column(type="string", length=20, nullable=false)
     */
    private $phoneNumber;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="integer", nullable=false)
     */
    private $amount;

    /**
     * @OneToMany(targetEntity="Ad", mappedBy="client")
     */
    private $ads;

    /**
     * @OneToMany(targetEntity="Offer", mappedBy="carrier")
     */
    private $offers;

    public function __construct() {
        $this->ads = new ArrayCollection();
        $this->offers = new ArrayCollection();
    }

    public function getId()
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getEmail()
    {
        return $this->email;
    }

    /**
     * @param mixed $email
     */
    public function setEmail($email): void
    {
        $this->email = $email;
    }

    /**
     * @return mixed
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @param mixed $name
     */
    public function setName($name): void
    {
        $this->name = $name;
    }

    /**
     * @return mixed
     */
    public function getFirstname()
    {
        return $this->firstname;
    }

    /**
     * @param mixed $firstname
     */
    public function setFirstname($firstname): void
    {
        $this->firstname = $firstname;
    }

    /**
     * @return mixed
     */
    public function getBirtiday()
    {
        return $this->birtiday;
    }

    /**
     * @param mixed $birtiday
     */
    public function setBirtiday($birtiday): void
    {
        $this->birtiday = $birtiday;
    }

    /**
     * @return mixed
     */
    public function getPhoneNumber()
    {
        return $this->phoneNumber;
    }

    /**
     * @param mixed $phoneNumber
     */
    public function setPhoneNumber($phoneNumber): void
    {
        $this->phoneNumber = $phoneNumber;
    }

    /**
     * @return mixed
     */
    public function getAmount()
    {
        return $this->amount;
    }

    /**
     * @param mixed $amount
     */
    public function setAmount($amount): void
    {
        $this->amount = $amount;
    }

    /**
     * @return Collection|Ad[]
     */
    public function getAds(): Collection
    {
        return $this->ads;
    }

    /**
     * @param mixed $ad
     */
    public function addAd($ad): void
    {
        $this->ads->add($ad);
    }

    /**
     * @param mixed $ad
     */
    public function removeAd($ad): void
    {
        $this->ads->removeElement($ad);
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