# HireHub
### Ce dépôt contient le projet pour l'UE MOBG6 à l'ESI.

**Le projet est codé en Kotlin et utilise le design pattern MVVM (Modèle-View-ViewModel)**

## Introduction
Bienvenue dans Hirehub, l'application Android qui rend la recherche et la réservation de rendez-vous avec des développeurs plus simples que jamais. Il est possible de voir les détails des développeurs.

Une fois que vous avez trouvé le développeur qui correspond à vos besoins, Hirehub vous permet de planifier des rendez-vous en choisissant une date et une heure qui vous conviennent. Le développeur, de son côté, recevra une notification pour les rendez-vous à venir et pourra accepter ou refuser les propositions en un seul clic.

Hirehub facilite également la gestion de votre emploi du temps grâce à sa fonctionnalité de suivi des rendez-vous. Vous pouvez garder une trace des rendez-vous passés et à venir, vous assurant de rester organisé et de ne rien manquer.

## Wireframe

<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/fbf0ad0a3ce9d6e97338ac7890278a75/54516_wireframe.png" alt="Wireframe">

## Démonstration
Le fragment de connexion permettant de se connecter
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/a093f10cf5923d74a02bf6672e5ebaaa/login.png" alt="SigIn" height=400 width=400>

**Le fragment d'inscription permettant de créer un compte (soit utilisateur, soit développeur)**
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/8f4451246eb1953417bd3994f58ee4f7/signup_1.png" alt="Signup_1" height=400 width=400>

<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/63fa5bf7f718d37151b54b36902a2b84/signup_2.png" alt="Signup_2" height=400 width=400>

**Le fragment qui permet la réinitialisation du mot de passe**
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/418814b444c957a53d6b14cba06d0d0a/password_recovery.png" alt="Password_recovery" height=400 width=400>

### <span style="color:red; font-weight:bold;">Utilisateur</span>
**Le fragment d'accueil qui affiche les rendez-vous à venir et passés**
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/24284e807b6555757506d223950cd726/home.png" alt="Home" height=400 width=400>

**Le fragment qui permet d'effectuer une recherche par nom ou par tri**
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/f9a671e74f23861239b6b95c55ebf86e/search.png" alt="Search" height=400 width=400>

**Le fragment qui affiche les détails d'un développeur**
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/12dca8cfe185f859b40bc76aae628769/details.png" alt="Details" height=400 width=400>

**Le fragment qui affiche un calendrier et permet d'effectuer une réservation de rendez-vous pour le développeur concerné**
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/be31b2adf1895f8911236e958ca0b0ea/calendar.png" alt="Calendar" height=400 width=400>

### <span style="color:red; font-weight:bold;">Développeur</span>
**Le fragment qui permet d'accepter ou de refuser un rendez-vous**
<br>
<img src="https://git.esi-bru.be/2022-2023/mobg6/projets/g54516/uploads/212e5055b7151cf61518ca84d5bb23b7/approval.png" alt="Approval" height=400 width=400>

## Structure du projet

````
.
├── database
│   ├── Converters.kt
│   ├── HireHubDB.kt
│   ├── dao
│   │   └── UserDao.kt
│   ├── dto
│   │   ├── AppointmentDto.kt
│   │   ├── DeveloperDto.kt
│   │   └── UserDto.kt
│   ├── entity
│   │   └── User.kt
│   ├── repository
│   │   ├── AppointmentRepository.kt
│   │   ├── DeveloperRepository.kt
│   │   └── UserRepository.kt
│   └── service
│       └── AuthService.kt
├── model
│   ├── FilterType.kt
│   ├── Utils.kt
│   ├── adapters
│   │   ├── DeveloperAdapter.kt
│   │   ├── DeveloperAppointmentAdapter.kt
│   │   ├── FilterAdapter.kt
│   │   ├── PendingAppointmentAdapter.kt
│   │   ├── TimeSlotAdapter.kt
│   │   └── UserAppointmentAdapter.kt
│   ├── factories
│   │   ├── AppointmentViewModelFactory.kt
│   │   ├── DeveloperViewModelFactory.kt
│   │   ├── ForgotPasswordModelFactory.kt
│   │   ├── HomeViewModelFactory.kt
│   │   ├── LoginViewModelFactory.kt
│   │   ├── PendingViewModelFactory.kt
│   │   ├── RegisterViewModelFactory.kt
│   │   └── SearchViewModelFactory.kt
│   ├── viewholders
│   │   ├── DeveloperAppointmentViewHolder.kt
│   │   ├── DeveloperViewHolder.kt
│   │   ├── FilterTypeViewHolder.kt
│   │   ├── PendingAppointmentViewHolder.kt
│   │   ├── TimeSlotViewHolder.kt
│   │   └── UserAppointmentViewHolder.kt
│   └── viewmodel
│       ├── AppointmentViewModel.kt
│       ├── DeveloperViewModel.kt
│       ├── ForgotPasswordViewModel.kt
│       ├── HomeViewModel.kt
│       ├── LoginViewModel.kt
│       ├── PendingViewModel.kt
│       ├── RegisterViewModel.kt
│       └── SearchViewModel.kt
└── ui
    ├── activities
    │   ├── LoginActivity.kt
    │   └── MainActivity.kt
    └── fragments
        ├── AboutFragment.kt
        ├── AppointmentFragment.kt
        ├── DeveloperFragment.kt
        ├── ForgotPasswordFragment.kt
        ├── HomeFragment.kt
        ├── LoginFragment.kt
        ├── PendingFragment.kt
        ├── RegisterFragment.kt
        ├── SearchFragment.kt
        └── SettingFragment.kt
````

## Auteur
Oumar Magomadov (54516@etu.he2b.be)
