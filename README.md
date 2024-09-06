# My vision of Clean Architecture (Hexagonal + Onion) implementation

"Clean" (Layered) Architecture is another way to achieve <b>separation of concerns</b>, <b>encapsulation</b> and <b>decoupling</b> by grouping code units based on their domain and functional role within the application. 

One of the main principle of this architecture is:
<b>We should never mix business-logik("useCase") and application-logik("infrastructure") within a one service class</b>.
This is because, it becomes practically impossible to separate these two types of logic, once they are implemented (mixed) together.

## My Approach to implementing Onion "Clean-Architecture" with Java (Domain-driven Design, 'Hexagon' Architecture) 
 
![cross the architectonic boundaries](/docs/img/onion1.JPG)


### Layer Structure:
1. <b>Application</b> - the boundary between the core application logic and the external world.
   - <b>Domain</b> Layer
     - All inner-<b>business logic</b> is contained here (only fields, accessors and aggregate methods and operations)
       - Example: Consistency rules like 'isPasswordIsValid(): >5' digits... (enforcing the aggregate’s business rules)
     - Private entities, events, inner operations and public Aggregat-Roots (as entry points)
     - <u>My approach: The interface (I-Domains) of entities with (consistency) methods on these entity-projections (+ generic<T, Z> in the service: only conditions between local i-entities).</u>
   - <b>UseCase</b> (abstract) Layer
     - <b>Separation of usecase-logik-methods from technical (low-level) service-help-methods</b>
     - <u>My approach: Abstract<T, Z> <b>Use Cases</b> (<b>Interactors</b> with I-Domains: create Entity, findByName, 'as a role X, I except special behavior'...)</u>
       - 1.define (or use same level-) DTOs (with other layers)
       - 2.define own (or use same level-) IGateway (technical interface-service-methods) 
       - Example: 'UserRegisterInteractor': injects und uses a (low-level) implementation of its own defined IGateway
     - Does not contain inner-business-logic (=inner rules within Domains). But should contain interactor-logik (extern actions with Entity-Aggregates).
     - No spring annotation are used in our business logic.
   - <b>Infrastructure</b> (backend impl) layer
     - Implementation of IGateway (technical helper-services, defined in the upper layer)
       - Example: 'JpaUserRegisterApplicationService'
     - <u>My approach: <b>Calling Use-Case</b>+ constructor-injection of own ServiceImpl in the abstract Usecase</u>
       - setting (higher) generics with concrete (low-level) <T: EntityImpl, Z: DTOImpl>
     - Provides the technical capabilities that <b>support</b> the layers above, such as persistence or messaging.
     - Implementation of portServices 
   - <b>Port</b> interfaces for adapters (public boundaries)
     - interfaces of portServices for the outside world (adapters)
     - DTOs for outside world requests and responses
2. <b>Adapter</b> Layer
  - <b>in</b> adapters:
    - specific user request (Controller Adapters)
  - <b>out</b> adapters:
    - persistence details + mapping (Infrastructure Adapters)
    - Presenter (ViewModel Adapters)
3. Extra "Main" (<b>Config</b>) Package
  - SpringConfig classes are divided separately for the 'domain', 'useCases' and 'infrastructure' layers.
    - f.e. scans for persistence beans in the external "adapter.out" package

- ![cca with spring boot](/docs/img/boot-cca.jpg)

- ![structure](/docs/img/onion1Tree.jpg)

### Special architecture decisions
- Domain layer: organized by domain packages. Example: "users.domain"
- Port Layer: The (command) requestDTO contains values with its own @Validation (so parameter-validation is not performed on the server, which is hidden from the user).
- Infrastructure Layer: separately naming PortServices, example:"UserPortService.java" (from the name we can recognize, we implement port interface). Other internal "services" will be used here too.
- UseCase Layer: example "onlineUseCase.java" (from the name we can recognize, we define a useCase and it belongs to "useCase" Layer)
- Main (Config) Layer: I specified that Spring scans persistence beans in the external "adapter.out.persistence" package.

### My differences to the base article:
- My modifications and corrections
    - 5 basic Layers ("application.domain", "application.usecase", "application.infrastructure", "application.port", "adapter", "Main") for the project package hierarchy. 
    - I believe Aggregates (DDD) should serve as an EntryPoint to Domain-Module (not just as a Factory)
    - Use records (Java 17) as Value-Objects
    - <b>ArchUnit</b> for enforce DDD Onion Architecture rules.
    - Spring Config from the "Main" layer injects DI Classes (assembling project respecting architectonic boundary)
    - Inject (port)Service as a Boundary-Interface


- ![onion layers](/docs/img/onion3.JPG)
- ![onion layers interactions](/docs/img/onion2.JPG)
- 
## Alternative layers structure and encapsulation

- ![alternative layers encapsulation](/docs/img/other_layers.JPG)
- ![DDD layers](/docs/img/ddd_layers.jpg)

### Used articles as a basis:

- [Clean Architecture with Spring Boot](https://www.baeldung.com/spring-boot-clean-architecture)
- [Architecture rules & ArchUnit check](https://www.jvt.me/posts/2022/01/28/spring-boot-onion-architecture/)
