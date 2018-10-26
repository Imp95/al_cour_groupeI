<?php
// src/Controller/PersistenceController.php
namespace App\Controller;

use App\Entity\User;
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
     * @Route("/persistence/switchmam", name="switchman")
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
            case 'connexion' :
                return $this->connexionAction($data['body']);
                break;
            case 'inscription' :
                throw new RuntimeException('non implémenté');
                break;
            case 'recherche_annonce' :
                throw new RuntimeException('non implémenté');
                break;
            case 'creation_offre' :
                throw new RuntimeException('non implémenté');
                break;
            case 'voir_offres' :
                throw new RuntimeException('non implémenté');
                break;
            case 'voir_contrats' :
                throw new RuntimeException('non implémenté');
                break;
            case 'update_contrats' :
                throw new RuntimeException('non implémenté');
                break;
            case 'historique_contrats_email' :
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
        $repository = $entityManager->getRepository(User::class);

        /** @var User $user */
        $user = $repository->findOneBy(array('email' => $body['email']));
        if ($user == null) {
            return $this->json(array(
                'status' => 'false',
                'body' => 'Cet utilisateur n\'existe pas !'
            ));
        }
        if (strcmp($user->getPassword(), $body['mdp']) !== 0) {
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