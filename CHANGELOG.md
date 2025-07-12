# Changelog

All notable changes to this project will be documented in this file.

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