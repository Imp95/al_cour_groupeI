<?php
// src/Entity/Contract.php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\OneToOne;
use Doctrine\ORM\Mapping\JoinColumn;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity
 */
class Contract
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="integer", nullable=false)
     */
    private $status;

    /**
     * @ORM\Column(type="integer", nullable=true)
     */
    private $depositAccused;

    /**
     * @ORM\Column(type="integer", nullable=true)
     */
    private $acknowledgement;

    /**
     * @OneToOne(targetEntity="Offer")
     * @JoinColumn(name="offer_id", referencedColumnName="id")
     */
    private $offer;

    public function getId()
    {
        return $this->id;
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
    public function getDepositAccused()
    {
        return $this->depositAccused;
    }

    /**
     * @param mixed $depositAccused
     */
    public function setDepositAccused($depositAccused): void
    {
        $this->depositAccused = $depositAccused;
    }

    /**
     * @return mixed
     */
    public function getAcknowledgement()
    {
        return $this->acknowledgement;
    }

    /**
     * @param mixed $acknowledgement
     */
    public function setAcknowledgement($acknowledgement): void
    {
        $this->acknowledgement = $acknowledgement;
    }

    /**
     * @return mixed
     */
    public function getOffer()
    {
        return $this->offer;
    }

    /**
     * @param mixed $offer
     */
    public function setOffer($offer): void
    {
        $this->offer = $offer;
    }

}