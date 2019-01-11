<?php
// src/Entity/Offer.php
namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\ORM\Mapping\ManyToOne;
use Doctrine\ORM\Mapping\JoinColumn;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * @ORM\Entity(repositoryClass="App\Repository\OfferRepository")
 */
class Offer
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @Assert\NotBlank()(
     *     message = "Aucune date de disponibilité n'a été saisi.",
     * )
     * @ORM\Column(type="date", nullable=false)
     */
    private $proposedDate;

    /**
     * @Assert\NotBlank()
     * @ORM\Column(type="integer", nullable=false)
     */
    private $status;

    /**
     * @ManyToOne(targetEntity="User", inversedBy="offers")
     * @JoinColumn(name="carrier_id", referencedColumnName="id")
     */
    private $carrier;

    /**
     * @ManyToOne(targetEntity="Ad", inversedBy="offers")
     * @JoinColumn(name="ad_id", referencedColumnName="id")
     */
    private $ad;

    public function getId()
    {
        return $this->id;
    }

    /**
     * @return mixed
     */
    public function getProposedDate()
    {
        return $this->proposedDate;
    }

    /**
     * @param mixed $proposedDate
     */
    public function setProposedDate($proposedDate): void
    {
        $this->proposedDate = $proposedDate;
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
    public function getCarrier()
    {
        return $this->carrier;
    }

    /**
     * @param mixed $carrier
     */
    public function setCarrier($carrier): void
    {
        $this->carrier = $carrier;
    }

    /**
     * @return mixed
     */
    public function getAd()
    {
        return $this->ad;
    }

    /**
     * @param mixed $ad
     */
    public function setAd($ad): void
    {
        $this->ad = $ad;
    }

}