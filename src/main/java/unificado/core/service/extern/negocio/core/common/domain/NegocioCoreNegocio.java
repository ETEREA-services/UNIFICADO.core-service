package unificado.core.service.extern.negocio.core.common.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioCoreNegocio {

    private Integer negocioId;
    private String nombre;
    private Integer negocioIdReal;
    private String databaseIpVpn;
    private String databaseIpLan;
    private String database;
    private String user;
    private Byte transferenciaStock;
    private Byte transferenciaValor;
    private String backendIpVpn;
    private String backendIpLan;
    private String backendPort;
    private String facturaServer;
    private String facturaPort;
    private Byte hasGateway;
    private Byte copyArticulo;

}
