<?php
// tests/Controller/ConnexionTest.php
namespace App\Tests\Controller;

use App\DataFixtures\AppFixtures;
use Doctrine\Common\DataFixtures\Executor\ORMExecutor;
use Doctrine\Common\DataFixtures\Loader;
use Doctrine\Common\DataFixtures\Purger\ORMPurger;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class ConnexionTest extends WebTestCase
{
    public static function setUpBeforeClass()
    {
        static::$kernel = static::createKernel();
        static::$kernel->boot();
        $em = static::$kernel->getContainer()
            ->get('doctrine')
            ->getManager();

        $loader = new Loader();
        $loader->addFixture(new AppFixtures());

        $purger = new ORMPurger($em);
        $executor = new ORMExecutor($em, $purger);
        $executor->execute($loader->getFixtures());

        parent::setUpBeforeClass();
    }

    public function testMissingParametersRequest()
    {
        $post_b = '{"action":"Connexion", "body":{"email":"aaa@aaa.com", "badparam":"null"}}';
        $client = $this->createClient();

        $client->request(
            'POST',
            '/receive_event',
            array(),
            array(),
            array('CONTENT_TYPE' => 'application/json'),
            $post_b
        );

        $this->assertTrue(
            $client->getResponse()->headers->contains(
                'Content-Type',
                'application/json'
            )
        );

        $data = json_decode($client->getResponse()->getContent(), true);

        $this->assertTrue(array_key_exists('status', $data));
        $this->assertTrue(strcmp($data['status'], 'false') === 0);
        $this->assertTrue(strpos($data['body'], 'obligatoires de parsage (connexion) ne sont') !== false);
    }

    public function testBadEmail()
    {
        $post_b = '{"action":"Connexion", "body":{"email":"aaa@aaa.com", "mdp":"strong"}}';
        $client = $this->createClient();

        $client->request(
            'POST',
            '/receive_event',
            array(),
            array(),
            array('CONTENT_TYPE' => 'application/json'),
            $post_b
        );

        $this->assertTrue(
            $client->getResponse()->headers->contains(
                'Content-Type',
                'application/json'
            )
        );

        $data = json_decode($client->getResponse()->getContent(), true);

        $this->assertTrue(array_key_exists('status', $data));
        $this->assertTrue(strcmp($data['status'], 'false') === 0);
        $this->assertTrue(strpos($data['body'], 'utilisateur n\'existe pas') !== false);
    }

    public function testBadPassword()
    {
        $post_b = '{"action":"Connexion", "body":{"email":"example1@email.com", "mdp":"azertyu"}}';
        $client = $this->createClient();

        $client->request(
            'POST',
            '/receive_event',
            array(),
            array(),
            array('CONTENT_TYPE' => 'application/json'),
            $post_b
        );

        $this->assertTrue(
            $client->getResponse()->headers->contains(
                'Content-Type',
                'application/json'
            )
        );

        $data = json_decode($client->getResponse()->getContent(), true);

        $this->assertTrue(array_key_exists('status', $data));
        $this->assertTrue(strcmp($data['status'], 'false') === 0);
        $this->assertTrue(strpos($data['body'], 'Mot de passe incorrect !') !== false);
    }

    public function testGoodConnexion()
    {
        $post_b = '{"action":"Connexion", "body":{"email":"example1@email.com", "mdp":"azerty"}}';
        $client = $this->createClient();

        $client->request(
            'POST',
            '/receive_event',
            array(),
            array(),
            array('CONTENT_TYPE' => 'application/json'),
            $post_b
        );

        $this->assertTrue(
            $client->getResponse()->headers->contains(
                'Content-Type',
                'application/json'
            )
        );

        $data = json_decode($client->getResponse()->getContent(), true);

        $this->assertTrue(array_key_exists('status', $data));
        $this->assertTrue(strcmp($data['status'], 'true') === 0);
        $this->assertTrue(strcmp(json_decode($data['body'], true)['name'], 'Doe') === 0);
    }
}