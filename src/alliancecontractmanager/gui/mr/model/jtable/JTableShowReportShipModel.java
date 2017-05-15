/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.gui.mr.model.jtable;

import alliancecontractmanager.db.entities.ContractEntity;
import alliancecontractmanager.logic.contract.formulas.ContractFormulas;
import alliancecontractmanager.logic.enumname.NameEnum;
import alliancecontractmanager.logic.manager.ManagerMicrimDB.ManagerSQLMicrimsDB;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author lele
 */
public class JTableShowReportShipModel extends JTableShowGenericModel{

    public JTableShowReportShipModel() {
        super(new String[]{NameEnum.DATEISSUED.getName(), NameEnum.SELLPRICE.getName(), 
         NameEnum.BUYORICE.getName(), NameEnum.PERCENTPROFIT.getName(),
         NameEnum.PROFIT.getName()});
    }

    @Override
    public Object getValueAt(int row, int column) {
        switch ( column) {
            case 0:
                return getContract(row).getDateIssuedFormatted();
            case 1: 
                return getContract(row).getPriceSell();
            case 2:                 
                 return getContract(row).getPriceBuy();
            case 3: 
                // DBG perché la cella si aggiorna lo stesso?
                
//                return ""+new DecimalFormat("##.#").format
//                 (getContract(row).getProfitPercent())+"%";
//                return getContract(row).getProfitPercent().toString();
            case 4: 
//                return getContract(row).getProfitPerShip().toString();
                
                Integer profitPerShip = getContract(row).getProfitPerShip();
//                return formatterValue(profitPerShip); 
                return formatterValue(profitPerShip.toString()); 
            default:
                return null;
        }
    }

    /**
     * DBG da unificare con il contract
     * trasform number from 200000000 to 200.000.000
     * @param value
     * @return 
     */
    private String formatterValue(String value){
        NumberFormat valueNF = NumberFormat.getNumberInstance(Locale.ITALY);
        valueNF.setGroupingUsed(true);
        String numberFormatted  = valueNF.format(Double.parseDouble(value));
        return numberFormatted;
    }
    
    
    @Override
    public boolean isCellEditable(int row, int column) {
        if ( column != 2) {
            return false;
        }
        return true;
    }

    
 @Override
    public void setValueAt(Object aValue, int row, int column) {
        // NO lui non sa l'id contract!!!!! Glielo dici tu ShowReportShipJPanel nel metodo writeValueToTable
        ContractEntity contractEntity = getObjectValue(aValue, row, column);
        
        ContractFormulas contractFormulas = new ContractFormulas(contractEntity);
        contractEntity.setProfitPercent(contractFormulas.getProfitPercent());
        contractEntity.setProfitPerShip(contractFormulas.getProfitPerShip() );
         
        contractEntity.setTempQualcosaDBG(1);
        // update sell price
        ManagerSQLMicrimsDB.getInstance().updateContract(contractEntity);     
        
        contractEntity = (ContractEntity) this.dataVector.elementAt(row);
        this.dataVector.setElementAt(contractEntity, row);
        fireTableCellUpdated(row, 3);
        fireTableCellUpdated(row, 4);
    }  

}

/*
        User user = (User) this.dataVector.elementAt(row);
        user.setName(aValue.toString());
        user.setSurname(aValue.toString());
        
        this.dataVector.setElementAt(user, column);
        fireTableCellUpdated(row, column +1 );
*/