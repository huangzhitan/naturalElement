/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.mess.service;

import com.leosys.app.item.entity.LAAMess;
import com.leosys.core.service.BaseServiceImplement;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author fanyouyong
 */
@Service("laaMessService")
@Transactional
public class LAAMessServiceImpl extends  BaseServiceImplement<LAAMess> implements LAAMessService{
    
}
