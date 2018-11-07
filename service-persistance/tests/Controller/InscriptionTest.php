<?php
// tests/Controller/InscriptionTest.php
namespace App\Tests\Controller;

use App\DataFixtures\AppFixtures;
use Doctrine\Common\DataFixtures\Executor\ORMExecutor;
use Doctrine\Common\DataFixtures\Loader;
use Doctrine\Common\DataFixtures\Purger\ORMPurger;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class InscriptionTest extends WebTestCase
{
    public function setUp()
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

        parent::setUp();
    }

    public function testMissingParametersRequest()
    {
        $post_b = '{"action":"Inscription", "body":{"email":"aaa@aaa.com", "password":"null", "badparam":"tsunchun"}}';
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
        $this->assertTrue(strpos($data['body'], 'obligatoires de parsage (inscription) ne sont') !== false);
    }

    public function testAlreadyUsedMailRequest()
    {
        $post_b = '{"action":"Inscription", "body":{"email":"example1@email.com", "password":"pass", "name":"Chun", "firstname":"Tsun", "birthday":"2000-01-20", "phone_number":"0411223344"}}';
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
        $this->assertTrue(strpos($data['body'], 'This value is already used.') !== false);
    }

    public function testWrongMailRequest()
    {
        $post_b = '{"action":"Inscription", "body":{"email":"examplemail.com", "password":"pass", "name":"Chun", "firstname":"Tsun", "birthday":"2000-01-20", "phone_number":"0411223344"}}';
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
        $this->assertTrue(strpos($data['body'], 'L\'email saisi n\'est pas valide.') !== false);
    }

    public function testEmptyFieldsRequest()
    {
        $post_b = '{"action":"Inscription", "body":{"email":"example@mail.com", "password":"pass", "name":"", "firstname":"", "birthday":"2000-01-20", "phone_number":""}}';
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
        $this->assertTrue(strpos($data['body'], 'This value should not be blank.') !== false);
    }

    public function testGoodInscriptionRequest()
    {
        $post_b = '{"action":"Inscription", "body":{"email":"example@mail.com", "password":"pass", "name":"Chun", "firstname":"Tsun", "birthday":"2000-01-20", "phone_number":"0411223344"}}';
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
        $this->assertTrue(strcmp($data['body']['name'], 'Chun') === 0);
        $this->assertTrue(strcmp($data['body']['phone_number'], '0411223344') === 0);
        $this->assertTrue(strcmp($data['body']['password'], 'pass') === 0);
    }

}