# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2026-01-20

### Added
- feat: Added WebClient.Builder bean in UnificadoConfiguration for reactive HTTP client support
- feat: Added validation for comprobanteAfipId in GeneraComprasService to skip vouchers without associated AFIP type

### Changed
- refactor: Updated Spring Boot parent to version 4.0.1 (major update with potential breaking changes)
- refactor: Updated Lombok annotations in domain classes (NegocioCoreCliente, NegocioCoreClienteMovimiento, etc.) to include @NoArgsConstructor and @AllArgsConstructor for better serialization support
- refactor: Updated JsonFormat pattern in NegocioCoreProveedorMovimiento from 'yyyy-MM-dd'T'HH:mm:ssZ' to 'yyyy-MM-dd'T'HH:mm:ssXXX' for improved timezone handling
- refactor: Removed spring-boot-starter-hateoas dependency
- refactor: Removed logback-core dependency from dependencyManagement
- refactor: Removed <executable>true</executable> from spring-boot-maven-plugin configuration
- refactor: Added jackson-databind dependency explicitly

### Fixed
- fix: Removed unused BigDecimal import in NegocioCoreClienteMovimientoService
- fix: Improved document number handling in recent commits (ASCII conversion for special characters, right-alignment of document numbers, detection of sales vouchers without AFIP association)

### Updated
- deps: Updated MySQL connector-j to 9.5.0
- deps: Updated springdoc-openapi-starter-webmvc-ui to 3.0.1
- deps: Updated commons-lang3 to 3.20.0
- deps: Updated mockwebserver to 5.3.2

## [0.5.1] - 2025-11-08

### Changed
- refactor: Updated API paths in CuentaController and CuentaNegocioController to include '/core' segment for better organization

### Fixed
- fix: Removed duplicate NegocioService class to resolve service conflicts
- fix: Added explicit mainClass configuration in pom.xml for proper executable build

## [0.5.0] - 2025-11-07

### Added
- feat: Added new services for generating purchases and sales reports (GeneraComprasService, GeneraVentasService, LIDGeneraComprasService, LIDGeneraVentasService)
- feat: Added new external services for client and provider movements (NegocioCoreClienteMovimientoService, NegocioCoreProveedorMovimientoService)
- feat: Added new domain models for external integrations (NegocioCoreCliente, NegocioCoreClienteMovimiento, NegocioCoreProveedor, NegocioCoreProveedorMovimiento, etc.)
- feat: Added utility class Tool for CUIT validation and file operations
- feat: Added new properties file for configuration (unificado.properties)

### Changed
- refactor: Reorganized package structure by moving classes to dedicated subpackages (cuenta, negocio, cuentanegocio, etc.)
- refactor: Renamed and restructured external services (NegocioContableService to NegocioCoreBalanceService)
- refactor: Updated application.yml with new configuration properties
- refactor: Removed test classes and updated build configuration
- refactor: Updated Dockerfiles and CI/CD pipeline for better compatibility

### Fixed
- fix: Improved error handling in purchase and sales generation services
- fix: Corrected package imports after refactoring

## [0.4.0] - 2025-07-13

### Added
- Added `spring.jpa.open-in-view: false` to `application.yml` to avoid performance issues.

### Changed
- Project name changed from "Eterea Core Service" to "Unificado Core Service".

### Fixed
- Corrección del pipeline por error de GitHub Actions.

## [0.3.1] - 2025-07-12

### Fixed
- Corrección del pipeline por error de GitHub Actions

## [0.3.0] - 2025-07-12

### Changed
- Se ha modificado el pipeline de CI/CD para construir la aplicación como una imagen nativa de GraalVM, mejorando el rendimiento y el tiempo de arranque.
- Se ha actualizado el `Dockerfile` a un formato multi-etapa para soportar la compilación con GraalVM.
- Se ha modernizado el workflow de GitHub Actions para utilizar las acciones oficiales de Docker para la publicación de imágenes.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [0.2.0] - 2025-07-11

### Added
- Calculos de totales de cuentas de negocios

### Changed
- Upgrading versions and urls changed
- Port changed
- Thining Dockerfile

## [0.1.0] - 2025-07-10

### Added
- Initial project setup with Spring Boot.
- Dockerfile for containerization.
- GitHub Actions for CI.
- Basic domain entities and repositories.
- Basic REST controllers.

### Changed
- Upgraded several dependencies.
- Refined Dockerfile for smaller image size.
- Changed application port.
- Updated URLs.