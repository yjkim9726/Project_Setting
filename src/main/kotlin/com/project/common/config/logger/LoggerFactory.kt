package com.project.common.config.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory
/**
 * @fileName LoggerFactory
 * @author yunjeong
 * @since  2023/10/16
 * @version 1.0
 *
 * @Modification Information
 * @
 * @  DATE          AUTHOR         NOTE
 * @ -----------   ------------   -------------------------------
 * @ 2023/10/16        yunjeong        최초 작성
 */
abstract class LoggerFactory {
    val log : Logger = LoggerFactory.getLogger(this.javaClass)
}