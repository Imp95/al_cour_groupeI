<?php
// src/Entity/User.php
namespace App\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\OneToMany;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;

/**
 * @ORM\Entity(repositoryClass="App\Repository\UserRepository")
 * @UniqueEntity("email")
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
     * @Assert\NotBlank()(
     *     message = "Aucun email n'a été saisi.",
     * )
     * @Assert\Email(
     *     message = "L'email saisi n'est pas valide.",
     *     checkMX = true
     * )
     * @ORM\Column(type="string", length=255, nullable=false, unique=true)
     */
    private $email;

    /**
     * @Assert\NotBlank()(
     *     message = "Aucun mot de passe n'a été saisi.",
     * )
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $password;

    /**
     * @Assert\NotBlank()(
     *     message = "Aucun nom n'a été saisi.",
     * )
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $name;

    /**
     * @Assert\NotBlank()(
     *     message = "Aucun prénom n'a été saisi.",
     * )
     * @ORM\Column(type="string", length=255, nullable=false)
     */
    private $firstname;

    /**
     * @Assert\NotBlank()(
     *     message = "Aucune date de naissance n'a été saisi.",
     * )
     * @ORM\Column(type="date", nullable=false)
     */
    private $birthday;

    /**
     * @Assert\NotBlank()(
     *     message = "Aucun numéro de téléphone n'a été saisi.",
     * )
     * @ORM\Column(type="string", length=20, nullable=false, name="phone_number")
     */
    private $phone_number;

    /**
     * @Assert\NotBlank()(
     *     message = "Aucun montant n'a été saisi.",
     * )
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
        $this->amount = 0;
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
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * @param mixed $password
     */
    public function setPassword($password): void
    {
        $this->password = $password;
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
    public function getBirthday()
    {
        return $this->birthday;
    }

    /**
     * @param mixed $birthday
     */
    public function setBirthday($birthday): void
    {
        $this->birthday = $birthday;
    }

    /**
     * @return mixed
     */
    public function getPhoneNumber()
    {
        return $this->phone_number;
    }

    /**
     * @param mixed $phoneNumber
     */
    public function setPhoneNumber($phoneNumber): void
    {
        $this->phone_number = $phoneNumber;
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