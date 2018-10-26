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
        $user1 = new User();
        $user1->setEmail('example1@email.com');
        $user1->setPassword('azerty');
        $user1->setName('Doe');
        $user1->setFirstname('John');
        $user1->setBirtiday(new \DateTime('1980-01-20'));
        $user1->setPhoneNumber('0122334455');
        $user1->setAmount(0);
        $manager->persist($user1);

        $user2 = new User();
        $user2->setEmail('example2@email.com');
        $user2->setPassword('password');
        $user2->setName('Bon');
        $user2->setFirstname('Jean');
        $user2->setBirtiday(new \DateTime('1977-03-18'));
        $user2->setPhoneNumber('0322334459');
        $user2->setAmount(50);
        $manager->persist($user2);

        $manager->flush();
    }
}