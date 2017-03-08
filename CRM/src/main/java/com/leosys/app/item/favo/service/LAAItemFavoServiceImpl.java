/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.favo.service;

import com.leosys.app.item.entity.LAAItemFavo;
import com.leosys.core.service.BaseServiceImplement;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fanyouyong
 */
@Service("laaItemFavosService")
@Transactional
public class LAAItemFavoServiceImpl extends BaseServiceImplement<LAAItemFavo> implements  LAAItemFavoService{
    
}
