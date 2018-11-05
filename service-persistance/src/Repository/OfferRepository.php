<?php
// src/Repository/OfferRepository.php

namespace App\Repository;

use App\Entity\User;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Symfony\Bridge\Doctrine\RegistryInterface;

class OfferRepository extends ServiceEntityRepository
{
    public function __construct(RegistryInterface $registry)
    {
        parent::__construct($registry, User::class);
    }

    /**
     * @param $id
     * @return mixed
     */
    public function findOneById($id)
    {
        $qb = $this->createQueryBuilder('o')
            ->andWhere('o.id = :id')
            ->setParameter('id', $id)
            ->getQuery();

        if ($qb->getArrayResult() == null) {
            return null;
        }

        return $qb->getArrayResult()[0];
    }
}