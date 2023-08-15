/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bj.dgi.GSBBackend.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author AKPONA Christian
 */
@Data
@NoArgsConstructor
public class ResetPassword {
    
    private String newPassword;
    private String username ;

    public ResetPassword(String newPassword, String username) {
        this.newPassword = newPassword;
        this.username = username;
    }
}
