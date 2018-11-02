<?php
// tests/Controller/FindAdTest.php
namespace App\Tests\Controller;

use App\DataFixtures\AppFixtures;
use Doctrine\Common\DataFixtures\Executor\ORMExecutor;
use Doctrine\Common\DataFixtures\Loader;
use Doctrine\Common\DataFixtures\Purger\ORMPurger;
use Symfony\Bundle\FrameworkBundle\Test\WebTestCase;

class FindAdTest extends WebTestCase
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
        $post_b = '{"action":"RechercheAnnonce", "body":{"departure_town":"Bad City", "badparam":"null"}}';
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
        $this->assertTrue(strpos($data['body'], 'obligatoires de parsage (recherche d\'annonce) ne sont') !== false);
    }

    public function testFindEmptyRequest()
    {
        $post_b = '{"action":"RechercheAnnonce", "body":{"departure_town":"null", "arrival_town":"null", "bagage":"null"}}';
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
        $this->assertTrue(count(json_decode($data['body'], true)) === 0);
    }

    public function testFindAllRequest()
    {
        $post_b = '{"action":"RechercheAnnonce", "body":{"departure_town":"", "arrival_town":"", "bagage":""}}';
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
        $this->assertTrue(count(json_decode($data['body'], true)) === 3);
    }

    public function testFindCustom1Request()
    {
        $post_b = '{"action":"RechercheAnnonce", "body":{"departure_town":"", "arrival_town":"Meat City", "bagage":"15x15x74"}}';
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
        $this->assertTrue(count(json_decode($data['body'], true)) === 1);
    }

    public function testFindCustom2Request()
    {
        $post_b = '{"action":"RechercheAnnonce", "body":{"departure_town":"", "arrival_town":"Meat City", "bagage":""}}';
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
        $this->assertTrue(count(json_decode($data['body'], true)) === 2);
    }
}