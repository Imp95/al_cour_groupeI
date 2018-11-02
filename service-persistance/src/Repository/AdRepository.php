<?php
// src/Repository/AdRepository.php

namespace App\Repository;

use App\Entity\Ad;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Symfony\Bridge\Doctrine\RegistryInterface;

class AdRepository extends ServiceEntityRepository
{
    public function __construct(RegistryInterface $registry)
    {
        parent::__construct($registry, Ad::class);
    }

    /**
     * @param $departureTown
     * @param $arrivalTown
     * @param $bagage
     * @return mixed
     */
    public function customFindByMultiParams($departureTown, $arrivalTown, $bagage)
    {
        $qb = $this->createQueryBuilder('a');

        if ($departureTown !== null && strlen($departureTown) > 0) {
            $qb->andWhere('a.departure_address LIKE :departure_town')
                ->setParameter('departure_town', '%' . $departureTown . '%');
        }
        if ($arrivalTown !== null && strlen($arrivalTown) > 0) {
            $qb->andWhere('a.arrival_address LIKE :arrival_town')
                ->setParameter('arrival_town', '%' . $arrivalTown . '%');
        }
        if ($bagage !== null && strlen($bagage) > 0) {
            $qb->andWhere('a.bagage LIKE :bagage')
                ->setParameter('bagage', '%' . $bagage . '%');
        }
        $q = $qb->getQuery();

        if ($q->getArrayResult() == null) {
            return null;
        }

        return $q->getArrayResult();
    }
}