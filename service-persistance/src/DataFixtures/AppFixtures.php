<?php
// src/DataFixtures/AppFixtures.php
namespace App\DataFixtures;

use App\Entity\Ad;
use App\Entity\Contract;
use App\Entity\Offer;
use App\Entity\User;
use Doctrine\Bundle\FixturesBundle\Fixture;
use Doctrine\Common\Persistence\ObjectManager;

class AppFixtures extends Fixture
{
    public function load(ObjectManager $manager)
    {
        /* users */
        $user1 = new User();
        $user1->setEmail('example1@email.com');
        $user1->setPassword('azerty');
        $user1->setName('Doe');
        $user1->setFirstname('John');
        $user1->setBirthday(new \DateTime('1980-01-20'));
        $user1->setPhoneNumber('0122334455');
        $user1->setAmount(0);
        $manager->persist($user1);

        $user2 = new User();
        $user2->setEmail('example2@email.com');
        $user2->setPassword('password');
        $user2->setName('Bon');
        $user2->setFirstname('Jean');
        $user2->setBirthday(new \DateTime('1977-03-18'));
        $user2->setPhoneNumber('0322334459');
        $user2->setAmount(50);
        $manager->persist($user2);

        /* ads */
        $ad1 = new Ad();
        $ad1->setDepartureDate(new \DateTime('2018-01-13'));
        $ad1->setArrivalDate(new \DateTime('2018-01-14'));
        $ad1->setBagage('15x15x15');
        $ad1->setPayment(50);
        $ad1->setStatus(0);
        $ad1->setClient($user1);
        $ad1->setDepartureAddress('3 rue swag 03333 Magic Town');
        $ad1->setArrivalAddress('3 rue nulle 09333 Meat City');
        $manager->persist($ad1);

        $ad2 = new Ad();
        $ad2->setDepartureDate(new \DateTime('2018-01-18'));
        $ad2->setArrivalDate(new \DateTime('2018-01-19'));
        $ad2->setBagage('15x15x30');
        $ad2->setPayment(50);
        $ad2->setStatus(0);
        $ad2->setClient($user2);
        $ad2->setDepartureAddress('3 rue swag 03333 Ville');
        $ad2->setArrivalAddress('3 rue nulle 09333 Villeuuu');
        $manager->persist($ad2);

        $ad3 = new Ad();
        $ad3->setDepartureDate(new \DateTime('2018-01-22'));
        $ad3->setArrivalDate(new \DateTime('2018-01-24'));
        $ad3->setBagage('15x15x74');
        $ad3->setPayment(45);
        $ad3->setStatus(0);
        $ad3->setClient($user1);
        $ad3->setDepartureAddress('3 rue swag 03333 Ville');
        $ad3->setArrivalAddress('3 rue nulle 09333 Meat City');
        $manager->persist($ad3);

        $manager->flush();
    }
}