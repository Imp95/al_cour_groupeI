<?php
// tests/Controller/BadSwitchmanTest.php
namespace App\Tests\Controller;

use App\DataFixtures\AppFixtures;
use Doctrine\Common\DataFixtures\Executor\ORMExecutor;
use Doctrine\Common\DataFixtures\Loader;
use Doctrine\Common\DataFixtures\Purger\ORMPurger;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class BadSwitchmanTest extends WebTestCase
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

    public function testNoJsonRequest()
    {
        $post_b = "malformed_data";
        $client = $this->createClient();

        $client->request(
            'POST',
            '/receive_event',
            array(),
            array(),
            array('CONTENT_TYPE' => 'text'),
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
        $this->assertTrue(strpos($data['body'], 'Le contenu JSON est mal formé.') !== false);

        $client->request(
            'POST',
            '/receive_event',
            array(),
            array(),
            array('CONTENT_TYPE' => 'application/json'),
            null
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
        $this->assertTrue(strpos($data['body'], 'contenu de la requête n\'est pas valide') !== false);
    }

    public function testMalformedRequestJson()
    {
        $post_b = "malformed_data";
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
        $this->assertTrue(strpos($data['body'], 'contenu JSON est mal formé') !== false);
    }

    public function testMissingParametersRequest()
    {
        $post_b = '{"action":"a", "nop":"n"}';
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
        $this->assertTrue(strpos($data['body'], 'obligatoires de parsage (globale) ne sont pas') !== false);
    }
}