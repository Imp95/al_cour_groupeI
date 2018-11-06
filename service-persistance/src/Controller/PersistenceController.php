<?php
// src/Controller/PersistenceController.php
namespace App\Controller;

use App\Entity\Ad;
use App\Entity\Offer;
use App\Entity\User;
use App\Repository\AdRepository;
use App\Repository\OfferRepository;
use App\Repository\UserRepository;
use http\Exception\RuntimeException;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Validator\Validator\ValidatorInterface;

class PersistenceController extends Controller
{
    /**
     * Endpoint du service de persistence
     *
     * @Route("/receive_event", name="switchman")
     * @param Request $request
     * @param ValidatorInterface $validator
     * @return Response
     */
    public function switchman(Request $request, ValidatorInterface $validator)
    {
        if (!$request->getContent()) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Le contenu de la requête n\'est pas valide.'
            ));
        }
        $data = json_decode($request->getContent(), true);
        if (json_last_error() !== JSON_ERROR_NONE) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Le contenu JSON est mal formé.'
            ));
        }

        if (!array_key_exists('action', $data) || !array_key_exists('body', $data)) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Les clés obligatoires de parsage (globale) ne sont pas présentes.'
            ));
        }

        switch ($data['action']) {
            case 'Connexion' :
                return $this->connexionAction($data['body']);
                break;
            case 'Inscription' :
                return $this->inscriptionAction($data['body'], $validator);
                break;
            case 'RechercheAnnonce' :
                return $this->findAdAction($data['body']);
                break;
            case 'CreationOffre' :
                return $this->creationOffreAction($data['body'], $validator);
                break;
            case 'VoirOffres' :
                throw new RuntimeException('non implémenté');
                break;
            case 'VoirContrats' :
                throw new RuntimeException('non implémenté');
                break;
            case 'UpdateContrats' :
                throw new RuntimeException('non implémenté');
                break;
            case 'HistoriqueContratsEmail' :
                throw new RuntimeException('non implémenté');
                break;
            default :
                return $this->json(array(
                    'status' => 'false',
                    'body' => 'L\'action demandée n\'existe pas.'
                ));
        }
    }

    /**
     * Action de connexion au service
     *
     * @param array $body
     * @return Response
     */
    private function connexionAction(array $body)
    {
        if (!array_key_exists('email', $body) || !array_key_exists('mdp', $body)) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Les clés obligatoires de parsage (connexion) ne sont pas présentes.'
            ));
        }

        $entityManager = $this->getDoctrine()->getManager();
        /** @var UserRepository $repository */
        $repository = $entityManager->getRepository(User::class);

        $user = $repository->findOneByEmail($body['email']);
        if ($user == null) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Cet utilisateur n\'existe pas !'
            ));
        }
        if (strcmp($user['password'], $body['mdp']) !== 0) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Mot de passe incorrect !'
            ));
        }

        return $this->json(array(
            'status' => 'true',
            'body' => json_encode($user)
        ));
    }

    /**
     * Action d'inscription au service
     *
     * @param array $body
     * @param ValidatorInterface $validator
     * @return Response
     */
    private function inscriptionAction(array $body, ValidatorInterface $validator)
    {
        if (!array_key_exists('email', $body) || !array_key_exists('password', $body)
            || !array_key_exists('name', $body) || !array_key_exists('firstname', $body)
            || !array_key_exists('birthday', $body) || !array_key_exists('phone_number', $body)) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Les clés obligatoires de parsage (inscription) ne sont pas présentes.'
            ));
        }

        $entityManager = $this->getDoctrine()->getManager();

        /** @var User $user */
        $user = new User();
        $user->setEmail($body['email']);
        $user->setPassword($body['password']);
        $user->setName($body['name']);
        $user->setFirstname($body['firstname']);
        $user->setBirtiday(new \DateTime($body['birthday']));
        $user->setPhoneNumber($body['phone_number']);

        $errors = $validator->validate($user);

        if (count($errors) > 0) {
            /*
             * Uses a __toString method on the $errors variable which is a
             * ConstraintViolationList object. This gives us a nice string
             * for debugging.
             */
            $errorsString = (string)$errors;

            return $this->json(array(
                'status' => 'false',
                'body' => json_encode($errorsString)
            ));
        }

        $entityManager->persist($user);
        $entityManager->flush();

        /** @var UserRepository $repository */
        $repository = $entityManager->getRepository(User::class);

        $userCreated = $repository->findOneByEmail($body['email']);
        return $this->json(array(
            'status' => 'true',
            'body' => json_encode($userCreated)
        ));
    }

    /**
     * Action de recherche d'annonce
     *
     * @param array $body
     * @return Response
     */
    private function findAdAction(array $body)
    {
        if (!array_key_exists('departure_town', $body) || !array_key_exists('arrival_town', $body)
            || !array_key_exists('bagage', $body)) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Les clés obligatoires de parsage (recherche d\'annonce) ne sont pas présentes.'
            ));
        }

        $entityManager = $this->getDoctrine()->getManager();
        /** @var AdRepository $repository */
        $repository = $entityManager->getRepository(Ad::class);

        $resultSearch = $repository->customFindByMultiParams($body['departure_town'], $body['arrival_town'], $body['bagage']);

        if ($resultSearch === null) {
            $resultSearch = array();
        }

        return $this->json(array(
            'status' => 'true',
            'body' => json_encode($resultSearch)
        ));
    }

    /**
     * Action de création d'offre
     *
     * @param array $body
     * @param ValidatorInterface $validator
     * @return Response
     */
    private function creationOffreAction(array $body, ValidatorInterface $validator)
    {
        if (!array_key_exists('ad_id', $body) || !array_key_exists('proposed_date', $body)
            || !array_key_exists('carrier_email', $body)) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Les clés obligatoires de parsage (création d\'offre) ne sont pas présentes.'
            ));
        }

        $entityManager = $this->getDoctrine()->getManager();

        /** @var AdRepository $repositoryAd */
        $repositoryAd = $entityManager->getRepository(Ad::class);
        /** @var UserRepository $repositoryUser */
        $repositoryUser = $entityManager->getRepository(User::class);

        /** @var Offer $offer */
        $offer = new Offer();
        $offer->setProposedDate(new \DateTime($body['proposed_date']));
        $offer->setCarrier($repositoryUser->findOneBy(array('email' => $body['carrier_email'])));
        $offer->setAd($repositoryAd->find(intval($body['ad_id'])));

        $errors = $validator->validate($offer);

        if (count($errors) > 0) {
            /*
             * Uses a __toString method on the $errors variable which is a
             * ConstraintViolationList object. This gives us a nice string
             * for debugging.
             */
            $errorsString = (string)$errors;

            return $this->json(array(
                'status' => 'false',
                'body' => json_encode($errorsString)
            ));
        }

        $entityManager->persist($offer);
        $entityManager->flush();

        /** @var OfferRepository $repository */
        $repository = $entityManager->getRepository(Offer::class);

        $userCreated = $repository->findOneById($offer->getId());
        return $this->json(array(
            'status' => 'true',
            'body' => json_encode($userCreated)
        ));
    }
}