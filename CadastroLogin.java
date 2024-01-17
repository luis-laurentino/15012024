import java.sql.*;
import java.util.*;

public class CadastroLogin {
    public static void main(String[] args) {

        int sair;
        sair = 0;
        
        Scanner scnSair = new Scanner(System.in);

        while (sair != 2) {
            Scanner scnCouL = new Scanner(System.in);

            System.out.println("Bem vindo ao SISTEMA");
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Cadastrar");
            System.out.println("2. Realizar Login");

            int escolhaCouL = scnCouL.nextInt();

            if (escolhaCouL == 1) {
                try {
                    Connection conn = App.conectar();

                    Scanner scnCadLogin = new Scanner(System.in);
                    Scanner scnCadNome = new Scanner(System.in);
                    Scanner scnCadSenha = new Scanner(System.in);

                    System.out.println("\nDigite o login que será cadastrado: ");
                    String strLogin = scnCadLogin.nextLine();
                    System.out.println("\nDigite o nome que será cadastrado: ");
                    String strNome = scnCadNome.nextLine();
                    System.out.println("\nDigite a senha que será cadastrada: ");
                    String strSenha = scnCadSenha.nextLine();

                    String strSqlInsert = "INSERT INTO `mysql_conector`.`tbl_login` (`login`, `nome`, `senha`) VALUES ('" + strLogin + "','" + strNome + "','" + strSenha + "');";

                    Statement stmSql = conn.createStatement();
                    stmSql.addBatch(strSqlInsert);
                    stmSql.executeBatch();

                    System.out.println("Ok! Login inserido com sucesso\n");
                    
                    
                    scnCadLogin.close();
                    scnCadNome.close();
                    scnCadSenha.close();
                    sair = 0;
                    System.out.println("\nDeseja voltar ao menu inicial ou Sair do programa?");
                    System.out.println("Escolha uma opção:");
                    System.out.println("1. Ir ao Menu principal");
                    System.out.println("2. Sair do programa");
                    sair = scnSair.nextInt();

                } catch (Exception e) {
                    System.err.println("Ops! Ocorreu um erro: " + e);
                }
            } else if (escolhaCouL == 2) {
                try {
                    Connection conn = App.conectar();

                    Scanner scnLoginAcesso = new Scanner(System.in);
                    Scanner scnSenhaAcesso = new Scanner(System.in);
                    Scanner scnOpcao = new Scanner(System.in);
                    Scanner scnUpdateNome = new Scanner(System.in);
                    Scanner scnUpdateSenha = new Scanner(System.in);

                    System.out.println("\nDigite o login de acesso: ");
                    String strBuscaLogin = scnLoginAcesso.nextLine();
                    System.out.println("\nDigite a senha de acesso: ");
                    String strBuscaPw = scnSenhaAcesso.nextLine();

                    String strSqlSelect = "SELECT * FROM `mysql_conector`.`tbl_login` WHERE `login` = '" + strBuscaLogin + "' AND `senha` = '" + strBuscaPw + "';";
                    Statement stmSql = conn.createStatement();
                    ResultSet rsSql = stmSql.executeQuery(strSqlSelect);
                    stmSql.close();

                    if (rsSql.next()) {
                        String nome = rsSql.getString("nome");
                        String senha = rsSql.getString("senha");

                        System.out.println("Login bem-sucedido!\n");
                        System.out.println("Nome: " + nome);
                        System.out.println("\nSenha: " + senha);

                        System.out.println("\nEscolha uma opção:");
                        System.out.println("1. Alterar Nome");
                        System.out.println("2. Alterar Senha");

                        int escolha = scnOpcao.nextInt();

                        if (escolha == 1) {
                            System.out.println("\nDigite o novo nome: ");
                            String novoNome = scnUpdateNome.next();
                            String strSqlUpdateNome = "UPDATE `mysql_conector`.`tbl_login` SET `nome` = '" + novoNome + "' WHERE `login` = '" + strBuscaLogin + "';";
                            
                            stmSql.addBatch(strSqlUpdateNome);
                            stmSql.executeBatch();
                            stmSql.close();

                            System.out.println("\nNome atualizado com sucesso.");
                            sair = 0;
                            System.out.println("\nDeseja voltar ao menu inicial ou Sair do programa?");
                            System.out.println("Escolha uma opção:");
                            System.out.println("1. Ir ao Menu principal");
                            System.out.println("2. Sair do programa");
                            sair = scnSair.nextInt();

                        } else if (escolha == 2) {
                            System.out.println("Após alterar sua senha você sera deslogado do sistema.\n");
                            System.out.println("\nDigite a nova senha: ");
                            String novaSenha = scnUpdateSenha.next();
                            String strSqlUpdateNome = "UPDATE `mysql_conector`.`tbl_login` SET `senha` = '" + novaSenha + "' WHERE `login` = '" + strBuscaLogin + "';";
                            
                            stmSql.addBatch(strSqlUpdateNome);
                            stmSql.executeBatch();
                            stmSql.close();
                            System.out.println("\nSenha atualizada com sucesso. Saindo do sistema");
                            sair = 2;

                        } else {
                            System.out.println("Opção inválida.");
                        }
                    } else {
                        System.out.println("Login ou senha incorretos.");
                    }
                    scnLoginAcesso.close();
                    scnSenhaAcesso.close();
                    scnOpcao.close();
                    scnUpdateNome.close();
                    scnUpdateSenha.close();

                } catch (Exception e) {
                    System.err.println("Ops! Ocorreu um erro: " + e);
                }
            } else {
                System.out.println("Opção inválida.");
            }

            scnCouL.close();
        }
        scnSair.close();
    }
}
