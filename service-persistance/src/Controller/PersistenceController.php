<?php
// src/Controller/PersistenceController.php
namespace App\Controller;

use App\Entity\User;
use App\Repository\UserRepository;
use http\Exception\RuntimeException;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class PersistenceController extends Controller
{
    /**
     * Endpoint du service de persistence
     *
     * @Route("/receive_event", name="switchman")
     * @param Request $request
     * @return Response
     */
    public function switchman(Request $request)
    {
        if ($request->getContentType() != 'json' || !$request->getContent()) {
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
                throw new RuntimeException('non implémenté');
                break;
            case 'RechercheAnnonce' :
                throw new RuntimeException('non implémenté');
                break;
            case 'CreationOffre' :
                throw new RuntimeException('non implémenté');
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
}